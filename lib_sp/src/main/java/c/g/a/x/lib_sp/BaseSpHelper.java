package c.g.a.x.lib_sp;

import android.content.Context;
import android.content.SharedPreferences;

class BaseSpHelper {

    SharedPreferences sp;

    //    context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    BaseSpHelper(Context context) {
        sp = context.getApplicationContext().getSharedPreferences(context.getApplicationContext().getPackageName() + this.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }


}
