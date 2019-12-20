package c.g.a.x.lib_support.android.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 工具类
 * Created by SmileXie on 2017/6/29.
 */

public final class StatusBarHelper {


    public static Integer statusBarHeight = null;
    public static Integer navigationHeight = null;


    private static void init(Activity activity) {
        getStatusBarHeight(activity);
        getNavigationBarHeight(activity);
    }


    /**
     * 通过反射的方式获取状态栏高度
     */
    private static void getStatusBarHeight(Activity activity) {
        if (statusBarHeight != null) return;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            statusBarHeight = 0;
        }
    }


    /**
     * 获取底部导航栏高度
     */
    public static void getNavigationBarHeight(Activity activity) {

        if (navigationHeight != null) return;

        if (!checkDeviceHasNavigationBar(activity)) {
            navigationHeight = 0;
            return;
        }

        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        navigationHeight = resources.getDimensionPixelSize(resourceId);

        //部分机器不需要这个高度设置为0 就可以正常展示。
        navigationHeight = 0;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar = false;
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }


    /**
     * 状态栏的颜色透明
     */
    public static void setStatusBarTransparent(Activity activity) {
        setStatusBarTransparent(activity, Color.TRANSPARENT);
    }

    /**
     * 状态栏的颜色
     */
    public static void setStatusBarTransparent(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    /**
     * 设置状态栏文字色值为深色调
     *
     * @param useDart 是否使用深色调
     */
    public static void setStatusTextColor(Activity activity, boolean useDart) {
        if (isFlyme()) {
            processFlyMe(activity, useDart);
            return;
        }
        if (isMIUI()) {
            processMIUI(activity, useDart);
            return;
        }

        if (useDart) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, navigationHeight);
    }
/////////////
    /**
     * 判断手机是否是小米
     */
    private static Boolean miui = null;

    public static boolean isMIUI() {
        if (miui != null) return miui;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));

            miui = properties.getProperty("ro.miui.ui.version.code", null) != null
                    || properties.getProperty("ro.miui.ui.version.name", null) != null
                    || properties.getProperty("ro.miui.internal.storage", null) != null;
        } catch (final IOException e) {
            miui = false;
        }
        return miui;
    }

    /**
     * 判断手机是否是魅族
     */
    private static Boolean flyme = null;

    public static boolean isFlyme() {
        if (flyme != null) return flyme;
        try {
            Build.class.getMethod("hasSmartBar");
            flyme = true;
        } catch (final Exception e) {
            flyme = false;
        }
        return flyme;
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(Activity activity, boolean isLightStatusBar) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
            int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
            Field field = instance.getDeclaredField("meizuFlags");
            field.setAccessible(true);
            int origin = field.getInt(lp);
            if (isLightStatusBar) {
                field.set(lp, origin | value);
            } else {
                field.set(lp, (~value) & origin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private static void processMIUI(Activity activity, boolean lightStatusBar) {

        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);

            Method extraFlagField = activity.getWindow().getClass().getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), lightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
