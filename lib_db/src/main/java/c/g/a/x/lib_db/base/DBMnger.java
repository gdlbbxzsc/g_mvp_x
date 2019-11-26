package c.g.a.x.lib_db.base;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import c.g.a.x.lib_db.application.DBHelper;
import c.g.a.x.lib_db.roomTest.RoomTest;
import c.g.a.x.lib_db.roomTest.RoomTestDao;


@Database(entities = {RoomTest.class}, version = MyMigrations.DB_VERSION, exportSchema = false)
public abstract class DBMnger extends RoomDatabase {

    private volatile static DBMnger mInstance;

    public static DBMnger getInstance() {
        if (mInstance == null) {
            synchronized (DBMnger.class) {
                if (mInstance == null)
                    mInstance = Room
                            .databaseBuilder(DBHelper.getInstance().application, DBMnger.class, DBHelper.getInstance().application.getPackageName() + "_db")
                            .addMigrations(MyMigrations.getUpdateList())
                            .allowMainThreadQueries()
                            .build();
            }
        }
        return mInstance;
    }

    public static void destroy() {
        mInstance = null;
    }

    public abstract RoomTestDao userDao();

}
