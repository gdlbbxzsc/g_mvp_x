package c.g.a.x.global_application;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;
import java.util.Map;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.SystemUtils;
import c.g.a.x.lib_support.base.BaseApplication;
import c.g.a.x.lib_support.views.toast.SysToast;


public class GlobalApplication extends BaseApplication {

    private static GlobalApplication instances;

    public static GlobalApplication getInstances() {
        return instances;
    }

    //用于界面间数据透传--by gdl key the Class.getname value the data you want
    private static final Map<String, Object> dataMap = new HashMap<>();

    private UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.e("GlobalApplication onCreate=1" + getPackageName());

        if (!getApplicationInfo().packageName.equals(SystemUtils.getCurProcessName(getApplicationContext())))
            return;
        Logger.e("GlobalApplication onCreate=2" + getPackageName());

        instances = this;

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

        userInfo = new UserInfo();

        Logger.e("=======GlobalApplication's  getPackageName:" + getPackageName());

        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application

        if (BuildConfig.app_mode)
            SysToast.showToastLong(getApplicationContext(), "已连接测试服务器!\n connected test server!");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public UserInfo getUserInfo() {
        return userInfo;
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
        ARouter.getInstance().build(Constant.LOGIN_ACTIVITY).navigation(context);
        return false;
    }

    public boolean checkLogin() {
        return userInfo.isLogin();
    }

    private final ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    };

}
