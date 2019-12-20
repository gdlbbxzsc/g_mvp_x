package c.g.a.x.lib_support.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.imagepicker.view.SystemBarTintManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


////这里注意下 因为在评论区发现有网友调用setRootViewFitsSystemWindows 里面 winContent.getChildCount()=0 导致代码无法继续
////是因为你需要在setContentView之后才可以调用 setRootViewFitsSystemWindows
//
////当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
//   StatusBarUtil.setRootViewFitsSystemWindows(this,true);
//           //设置状态栏透明
//           StatusBarUtil.setTranslucentStatus(this);
//           //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
//           //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//           if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//           //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//           //这样半透明+白=灰, 状态栏的文字能看得清
//           StatusBarUtil.setStatusBarColor(this,0x55000000);
//           }
public class StatusBarHelper1 {

    //代码实现android:fitsSystemWindows
    public static void setRootViewFitsSystemWindows(Activity activity, boolean fitSystemWindows) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        ViewGroup winContent = activity.findViewById(android.R.id.content);
        if (winContent.getChildCount() <= 0) return;
        ViewGroup rootView = (ViewGroup) winContent.getChildAt(0);
        if (rootView == null) return;
        rootView.setFitsSystemWindows(fitSystemWindows);

//        activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, 0?);
    }


    public static boolean isHaveNavigationBar(Context context) {

        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) return rs.getBoolean(id);

        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("0".equals(navBarOverride)) return true;

//            if ("1".equals(navBarOverride)) return false;

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object obj = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            result = context.getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        int resourceId = activity.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) result = activity.getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    public static void setStatusBarTransparent(Activity activity) {
        setStatusBarColor(activity, Color.TRANSPARENT);
    }

    //设置状态栏颜色
//    @TargetApi(19)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = activity.getWindow();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            //int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);

            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);//显示状态栏
            systemBarTintManager.setStatusBarTintColor(color);//设置状态栏颜色
        }
    }

    //    导航栏颜色
    public static void setNavigationBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }


    public static void setStatusBarThemeDark(Activity activity) {
        setStatusBarTheme(activity, true);
    }

    public static void setStatusBarThemeLight(Activity activity) {
        setStatusBarTheme(activity, false);
    }

    /**
     * 设置状态栏深色浅色切换
     */
    public static void setStatusBarTheme(Activity activity, boolean dark) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();

            int vis = decorView.getSystemUiVisibility();

            if (dark) vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            else vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            if (decorView.getSystemUiVisibility() != vis) decorView.setSystemUiVisibility(vis);

        } else if (OSUtils.isMiui()) {
            setMiuiUI(activity, dark);
        } else if (OSUtils.isFlyme()) {
            setFlymeUI(activity, dark);
        }
    }


    //设置Flyme 状态栏深色浅色切换
    private static void setFlymeUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置MIUI 状态栏深色浅色切换
    private static void setMiuiUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            Class<?> clazz = activity.getWindow().getClass();
            @SuppressLint("PrivateApi") Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getDeclaredMethod("setExtraFlags", int.class, int.class);
            extraFlagField.setAccessible(true);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final class OSUtils {

        private static final String ROM_MIUI = "MIUI";
        private static final String ROM_EMUI = "EMUI";
        private static final String ROM_FLYME = "FLYME";
        private static final String ROM_OPPO = "OPPO";
        private static final String ROM_SMARTISAN = "SMARTISAN";
        private static final String ROM_VIVO = "VIVO";
        private static final String ROM_QIKU = "QIKU";

        private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
        private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
        private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
        private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
        private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";

        private static String name;
        private static String version;

        public static String getName() {
            if (name == null) getNameVersion();
            return name;
        }

        public static String getVersion() {
            if (version == null) getNameVersion();
            return version;
        }

        public static boolean isEmui() {
            return check(ROM_EMUI);
        }

        public static boolean isMiui() {
            return check(ROM_MIUI);
        }

        public static boolean isVivo() {
            return check(ROM_VIVO);
        }

        public static boolean isOppo() {
            return check(ROM_OPPO);
        }

        public static boolean isFlyme() {
            return check(ROM_FLYME);
        }

        public static boolean is360() {
            return check(ROM_QIKU) || check("360");
        }

        public static boolean isSmartisan() {
            return check(ROM_SMARTISAN);
        }

        private static boolean check(String rom) {
            if (name == null) getNameVersion();
            return name.equals(rom);
        }

        private static void getNameVersion() {
            if (!TextUtils.isEmpty(version = getProp(KEY_VERSION_MIUI))) {
                name = ROM_MIUI;
            } else if (!TextUtils.isEmpty(version = getProp(KEY_VERSION_EMUI))) {
                name = ROM_EMUI;
            } else if (!TextUtils.isEmpty(version = getProp(KEY_VERSION_OPPO))) {
                name = ROM_OPPO;
            } else if (!TextUtils.isEmpty(version = getProp(KEY_VERSION_VIVO))) {
                name = ROM_VIVO;
            } else if (!TextUtils.isEmpty(version = getProp(KEY_VERSION_SMARTISAN))) {
                name = ROM_SMARTISAN;
            } else {
                version = Build.DISPLAY;
                if (version.toUpperCase().contains(ROM_FLYME)) {
                    name = ROM_FLYME;
                } else {
                    version = Build.UNKNOWN;
                    name = Build.MANUFACTURER.toUpperCase();
                }
            }
        }

        private static String getProp(String name) {
            BufferedReader input = null;
            try {
                Process p = Runtime.getRuntime().exec("getprop " + name);
                input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
                String line = input.readLine();
                input.close();
                return line;
            } catch (IOException ex) {
                return null;
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}




