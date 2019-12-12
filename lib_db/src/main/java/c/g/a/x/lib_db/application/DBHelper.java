package c.g.a.x.lib_db.application;

import android.app.Application;

import androidx.room.Room;

import c.g.a.x.lib_db.BuildConfig;
import c.g.a.x.lib_db.base.DBMnger;
import c.g.a.x.lib_db.base.MyMigrations;
import c.g.a.x.lib_db.objectbox.MyObjectBox;
import c.g.a.x.lib_support.android.utils.Logger;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by Administrator on 2019/9/6.
 */

public final class DBHelper {

    public static Application application;

//    public static DBHelper getInstance() {
//        return InnerInstance.INSTANCE;
//    }

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
        boxStore = MyObjectBox.builder().androidContext(application.getApplicationContext()).build();
        if (BuildConfig.app_mode) {//开启浏览器访问ObjectBox
            boolean started = new AndroidObjectBrowser(boxStore).start(application.getApplicationContext());
            Logger.e("AndroidObjectBrowser start===>", started, " 浏览器访问 = http://localhost:8090/index.html");
        }
    }


//    public void aa() {
//        Box<Playlist> userEntityBox = boxStore.boxFor(Playlist.class);
//    }


    /////////////
    private volatile DBMnger dbMnger;

    public static DBMnger getDBMnger() {
        return InnerInstance.INSTANCE.dbMnger;
    }

    private void initDBMnger() {
        dbMnger = Room
                .databaseBuilder(application, DBMnger.class, application.getPackageName() + "_db")
                .addMigrations(MyMigrations.getUpdateList())
                .allowMainThreadQueries()
                .build();
    }

}
