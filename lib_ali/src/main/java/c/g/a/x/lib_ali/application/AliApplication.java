package c.g.a.x.lib_ali.application;

import android.content.res.Configuration;

import c.g.a.x.applogic_annotation.AppLogic;
import c.g.a.x.applogic_api.base.BaseAppLogic;
import c.g.a.x.lib_support.android.utils.Logger;


/**
 * Created by Administrator on 2019/9/6.
 */


@AppLogic
public class AliApplication extends BaseAppLogic {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("AliApplication onCreate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.e("AliApplication onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logger.e("AliApplication onTrimMemory level:" + level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.e("AliApplication onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.e("AliApplication onConfigurationChanged");
    }
}

