//package c.g.a.x.lib_db.application;
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
//public class DBApplication extends BaseAppLogic {
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Logger.e("DBApplication onCreate");
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        Logger.e("DBApplication onLowMemory");
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        Logger.e("DBApplication onTrimMemory level:" + level);
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        Logger.e("DBApplication onTerminate");
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Logger.e("DBApplication onConfigurationChanged");
//    }
//}
//
