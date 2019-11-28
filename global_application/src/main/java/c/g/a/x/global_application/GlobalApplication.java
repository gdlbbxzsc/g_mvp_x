package c.g.a.x.global_application;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;
import java.util.Map;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_base.BaseApplication;
import c.g.a.x.lib_sp.SpMnger;
import c.g.a.x.lib_support.android.utils.FileHelper;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.SystemUtils;


//这里继承 dbapp的话 就有数据库 如果调用baseapp的话就没有数据库
public class GlobalApplication extends BaseApplication {

    public static GlobalApplication instances;

    public static GlobalApplication getInstances() {
        return instances;
    }

    //用于界面间数据透传--by gdl key the Class.getname value the data you want
    private static Map<String, Object> dataMap = new HashMap();

    public UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.e("GlobalApplication onCreate=1" + getPackageName());

        if (!getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(getApplicationContext())))
            return;
        Logger.e("GlobalApplication onCreate=2" + getPackageName());

        instances = this;

        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());

        userInfo = UserInfo.getInstance();

        Logger.e("=======GlobalApplication's  getPackageName:" + getPackageName());
        FileHelper.init(getPackageName());


        SpMnger.getInstance().init(this);


        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }


    /////////////////////
    public static Object getDataMapData(String key) {
        Object obj = dataMap.get(key);
        dataMap.remove(key);
        return obj;
    }

    public static void setDataMapData(String key, Object val) {
        dataMap.put(key, val);
    }

    ///////////////////////
    public boolean checkLogin4GoLogin(Context context) {

        if (userInfo.isLogin()) return true;
//        context.startActivity(new Intent(context, LoginActivity.class));
        ARouter.getInstance().build(Constant.LOGIN_ACTIVITY).navigation(context);
        return false;
    }

    public boolean checkLogin() {
        return userInfo.isLogin();
    }


    private class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }


}
