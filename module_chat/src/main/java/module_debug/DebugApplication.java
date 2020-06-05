package module_debug;


import c.g.a.x.global_application.GlobalApplication;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.SystemUtils;

public class DebugApplication extends GlobalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("DebugApplication onCreate=1" + getPackageName());

        if (!getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(getApplicationContext())))
            return;

        Logger.e("DebugApplication onCreate=2" + getPackageName());

    }
}
