//package c.g.a.x.module_main.application;
//
//import android.content.res.Configuration;
//
//import c.g.a.x.applogic_annotation.AppLogic;
//import c.g.a.x.applogic_api.base.BaseAppLogic;
//import c.g.a.x.lib_support.android.utils.Logger;
//
//
///**
// * Created by Administrator on 2019/9/6.
// */
//
//
//@AppLogic
//public class MainApplication extends BaseAppLogic {
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Logger.e("MainApplication onCreate");
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        Logger.e("MainApplication onLowMemory");
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        Logger.e("MainApplication onTrimMemory level:" + level);
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        Logger.e("MainApplication onTerminate");
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Logger.e("MainApplication onConfigurationChanged");
//    }
//}
//
