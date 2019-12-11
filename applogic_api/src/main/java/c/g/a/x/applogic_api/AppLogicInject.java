package c.g.a.x.applogic_api;

import android.app.Application;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import c.g.a.x.applogic_api.base.BaseAppLogic;
import c.g.a.x.applogic_api.base.IAppLogicFullNamesLoader;
import c.g.a.x.applogic_api.utils.ClassUtils;

public class AppLogicInject {

    private static final String INTERFACE_IAPPLOGICFULLNAMESLOADER_IMPL_PACKAGE = "com.android.iapplogicfullnamesloader.impl";

    private static final List<String> appLogicFullNameList = new ArrayList<>();
    private static Application mApplication;
    private static final ArrayList<BaseAppLogic> appLogicList = new ArrayList<>();

    public static void inject(Application application) {
        mApplication = application;
        appLogicFullNameList.clear();
        try {
            Set<String> set = ClassUtils.getFileNameByPackageName(mApplication.getApplicationContext(), INTERFACE_IAPPLOGICFULLNAMESLOADER_IMPL_PACKAGE);

            for (String clz : set) {
                try {
                    Class<?> clazz = Class.forName(clz);
                    IAppLogicFullNamesLoader appLogic = (IAppLogicFullNamesLoader) clazz.newInstance();
                    appLogic.loadFullNames(appLogicFullNameList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onCreate() {
        if (appLogicFullNameList == null || appLogicFullNameList.isEmpty()) return;

        for (String appLogicFullName : appLogicFullNameList) {
            createAppLogic(appLogicFullName);
        }
    }


    private static void createAppLogic(String appLogicFullName) {
        try {
            Class<?> clazz = Class.forName(appLogicFullName);

            BaseAppLogic appLogic = (BaseAppLogic) clazz.newInstance();

            appLogicList.add(appLogic);

            appLogic.setApplication(mApplication);
            appLogic.onCreate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onTerminate() {
        if (appLogicList.isEmpty()) return;
        for (BaseAppLogic appLogic : appLogicList) {
            appLogic.onTerminate();
        }
    }

    public static void onLowMemory() {
        if (appLogicList.isEmpty()) return;
        for (BaseAppLogic appLogic : appLogicList) {
            appLogic.onLowMemory();
        }
    }

    public static void onTrimMemory(int level) {
        if (appLogicList.isEmpty()) return;
        for (BaseAppLogic appLogic : appLogicList) {
            appLogic.onTrimMemory(level);
        }
    }

    public static void onConfigurationChanged(Configuration newConfig) {
        if (appLogicList.isEmpty()) return;
        for (BaseAppLogic appLogic : appLogicList) {
            appLogic.onConfigurationChanged(newConfig);
        }
    }

}
