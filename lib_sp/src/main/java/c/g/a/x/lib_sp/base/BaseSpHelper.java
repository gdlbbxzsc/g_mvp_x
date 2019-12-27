package c.g.a.x.lib_sp.base;

import android.content.Context;
import android.content.SharedPreferences;

import c.g.a.x.lib_sp.BuildConfig;
import c.g.a.x.lib_support.base.BaseApplication;

public abstract class BaseSpHelper {

    protected SharedPreferences sp;

    //    context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    public BaseSpHelper() {
        sp = BaseApplication.getInstances().getSharedPreferences(BuildConfig.project_applicationId + this.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

}
