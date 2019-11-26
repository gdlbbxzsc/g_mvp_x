package c.g.a.x.lib_http.application;

import android.content.res.Configuration;

import c.g.a.x.applogic_annotation.AppLogic;
import c.g.a.x.applogic_api.base.BaseAppLogic;
import c.g.a.x.lib_support.android.utils.Logger;


/**
 * Created by Administrator on 2019/9/6.
 */


@AppLogic
public class HttpApplication extends BaseAppLogic {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e("HttpApplication onCreate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.e("HttpApplication onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logger.e("HttpApplication onTrimMemory level:" + level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.e("HttpApplication onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.e("HttpApplication onConfigurationChanged");
    }
}

