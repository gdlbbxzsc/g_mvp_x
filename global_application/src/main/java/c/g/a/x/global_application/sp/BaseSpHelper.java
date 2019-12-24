package c.g.a.x.global_application.sp;

import android.content.Context;
import android.content.SharedPreferences;

import c.g.a.x.global_application.BuildConfig;
import c.g.a.x.global_application.GlobalApplication;

class BaseSpHelper {

    SharedPreferences sp;

    //    context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    BaseSpHelper() {
        sp = GlobalApplication.getInstances().getSharedPreferences(BuildConfig.project_applicationId + this.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }

}
