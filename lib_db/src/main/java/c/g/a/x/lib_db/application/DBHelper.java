package c.g.a.x.lib_db.application;

import android.app.Application;

/**
 * Created by Administrator on 2019/9/6.
 */

public final class DBHelper {

    //    private static volatile BoxStore boxStore;
//    private BoxHelper() {
//        boxStore = MyObjectBox.builder().androidContext(context).build();
//    }

    public static DBHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final DBHelper INSTANCE = new DBHelper();
    }

    private DBHelper() {
    }

    public Application application;
}
