package c.g.a.x.lib_weixin.application;

import android.content.res.Configuration;

import c.g.a.x.applogic_annotation.AppLogic;
import c.g.a.x.applogic_api.base.BaseAppLogic;
import c.g.a.x.lib_support.android.utils.Logger;


/**
 * Created by Administrator on 2019/9/6.
 */


@AppLogic
public class WxApplication extends BaseAppLogic {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("WxApplication onCreate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.e("WxApplication onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logger.e("WxApplication onTrimMemory level:" + level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.e("WxApplication onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.e("WxApplication onConfigurationChanged");
    }
}

