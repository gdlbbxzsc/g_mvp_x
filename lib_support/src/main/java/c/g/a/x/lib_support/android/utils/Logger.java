package c.g.a.x.lib_support.android.utils;

import android.util.Log;

import c.g.a.x.lib_support.BuildConfig;


public final class Logger {

    private static final String DEFAULT_TAG = "===>";

    public static void e(Object... obj) {
        if (BuildConfig.LOG) Log.e(DEFAULT_TAG, getMsg(obj));
    }

    public static void d(Object... obj) {
        if (BuildConfig.LOG) Log.d(DEFAULT_TAG, getMsg(obj));
    }

    public static void i(Object... obj) {
        if (BuildConfig.LOG) Log.i(DEFAULT_TAG, getMsg(obj));
    }

    public static void w(Object... obj) {
        if (BuildConfig.LOG) Log.w(DEFAULT_TAG, getMsg(obj));
    }

    private static String getMsg(Object... object) {
        if (object == null || object.length <= 0) return " no msg ";

        StringBuilder s = new StringBuilder();
        for (Object str : object) {
            if (str == null)
                s.append("-unknow-");
            else
                s.append(str);
        }
        return s.toString();
    }
}
