package c.g.a.x.g_mvp_x;



import c.g.a.x.global_application.GlobalApplication;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.SystemUtils;


public class MyApplication extends GlobalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("MyApplication onCreate=1" + getPackageName());

        if (!getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(getApplicationContext())))
            return;

        Logger.e("MyApplication onCreate=2" + getPackageName());
 
    }
}
