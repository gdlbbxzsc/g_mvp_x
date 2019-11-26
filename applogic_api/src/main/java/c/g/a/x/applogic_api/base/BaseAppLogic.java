package c.g.a.x.applogic_api.base;

import android.app.Application;
import android.content.res.Configuration;

public abstract class BaseAppLogic {

    private Application application;


    public BaseAppLogic() {
    }

    public void onCreate() {
    }

    public void onTerminate() {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
