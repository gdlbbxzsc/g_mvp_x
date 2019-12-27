package c.g.a.x.lib_db.application;

import androidx.room.Room;

import c.g.a.x.lib_db.BuildConfig;
import c.g.a.x.lib_db.base.room.DBMnger;
import c.g.a.x.lib_db.base.room.MyMigrations;
import c.g.a.x.lib_db.objectboxTest.MyObjectBox;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.base.BaseApplication;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by Administrator on 2019/9/6.
 */

public final class DBHelper {

    private static class InnerInstance {
        private static final DBHelper INSTANCE = new DBHelper();
    }

    private DBHelper() {
        initObjectBox();
        initDBMnger();
    }

    /////////////////
    private BoxStore boxStore;

    public static BoxStore getBoxStore() {
        return InnerInstance.INSTANCE.boxStore;
    }

    private void initObjectBox() {
        //第一次没运行之前，MyObjectBox默认会有报错提示，可以忽略。创建实体类， make之后报错就会不提示
        boxStore = MyObjectBox.builder().androidContext(BaseApplication.getInstances().getApplicationContext()).build();
        if (BuildConfig.app_mode) {//开启浏览器访问ObjectBox
            boolean started = new AndroidObjectBrowser(boxStore).start(BaseApplication.getInstances().getApplicationContext());
            Logger.e("AndroidObjectBrowser start===>", started, " 浏览器访问: http://localhost:8090/index.html", " 如果不好使执行此命令监听端口: adb forward tcp:8090 tcp:8090");
        }
    }

    /////////////
    private volatile DBMnger dbMnger;

    public static DBMnger getDBMnger() {
        return InnerInstance.INSTANCE.dbMnger;
    }

    private void initDBMnger() {
        dbMnger = Room
                .databaseBuilder(BaseApplication.getInstances(), DBMnger.class, BaseApplication.getInstances().getPackageName() + "_db")
                .addMigrations(MyMigrations.getUpdateList())
                .allowMainThreadQueries()
                .build();
    }

}
