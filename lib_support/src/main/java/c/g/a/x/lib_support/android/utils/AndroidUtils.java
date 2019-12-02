package c.g.a.x.lib_support.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class AndroidUtils {

    @SuppressLint("MissingPermission")
    public static final String getDeviceId(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            if (tm == null) return "unknowdevice";

            return tm.getDeviceId();

        } catch (Exception e) {
            e.printStackTrace();
            return "unknowdevice";
        }
    }

    //////////////////////
    public static boolean isWIFI(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return null != info && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return null != info && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static boolean hasNetWork(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return null != info && info.isAvailable();
    }

    public static boolean hasNetWorks(Context context) {
        NetworkInfo[] info = getNetworkInfos(context);
        if (info == null) return false;

        for (NetworkInfo anInfo : info) {
            if (anInfo.getState() != NetworkInfo.State.CONNECTED) continue;
            return true;
        }
        return false;
    }


    private static final NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (null == manager)
            return null;
        return manager.getActiveNetworkInfo();
    }

    private static final NetworkInfo[] getNetworkInfos(Context context) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (null == manager)
            return null;
        return manager.getAllNetworkInfo();
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    ///////////////////////////
    public static boolean gpsCheck(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (lm == null) return false;

        boolean boolgp = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        boolean boolgn = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return boolgp || boolgn;
    }

    public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;

        double a = radLat1 - radLat2;
        double b = (lng1 * Math.PI / 180.0) - (lng2 * Math.PI / 180.0);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    ////////////////////////////
    public static String getVersionName(Context context) throws Exception {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) throws Exception {
        return getPackageInfo(context).versionCode;
    }

    public static PackageInfo getPackageInfo(Context context) throws Exception {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(context.getPackageName(), 0);
    }

    /////////////////////////////
    public static String readFileFromAsset2String(Context context, String fileName) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////
    public static void copyText(Context context, String str) {
        copy(context, ClipData.newPlainText("", str));
    }

    public static void copyUri(Context context, String str) {
        copy(context, ClipData.newRawUri("", Uri.parse(str)));
    }

    public static void copyUri(Context context, Uri uri) {
        copy(context, ClipData.newRawUri("", uri));
    }

    public static void copyIntent(Context context, Intent intent) {
        copy(context, ClipData.newIntent("", intent));
    }

    public static void copy(Context context, ClipData clipData) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(clipData);
    }

    public static String getPrimaryClipText(Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            ClipData data = cm.getPrimaryClip();
            ClipData.Item item = data.getItemAt(0);
            return item.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void addPrimaryClipChangedListener(Context context, ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.addPrimaryClipChangedListener(listener);
    }

    public static void removePrimaryClipChangedListener(Context context, ClipboardManager.OnPrimaryClipChangedListener listener) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.removePrimaryClipChangedListener(listener);
    }

    //////////////////////////
    public static void hideSoftInput(Activity context) {
        View view = context.getCurrentFocus();
        if (view == null) return;

        IBinder binder = view.getWindowToken();
        if (binder == null) return;


//        InputMethodManager imm = (InputMethodManager) getSystemService(Context
//        .INPUT_METHOD_SERVICE);
//        if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binder, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //////////////////////////

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static float getHeightInPx(Context context) {
        return (float) context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getWidthInPx(Context context) {
        return (float) context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return px2dip(context, height);
    }

    public static int getWidthInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return px2dip(context, height);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }
}
