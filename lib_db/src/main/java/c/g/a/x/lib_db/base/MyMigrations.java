package c.g.a.x.lib_db.base;

import androidx.room.migration.Migration;

public final class MyMigrations {

    public static final int DB_VERSION = 1;

    public static Migration[] getUpdateList() {

        Migration[] update_Migration_array = {

//                new Migration(1, 2) {
//                    @Override
//                    public void migrate(SupportSQLiteDatabase database) {
//                        database.execSQL("CREATE TABLE `second_table` (`id` INTEGER, `name` TEXT, PRIMARY KEY(`id`))");
//                    }
//                }

        };

        return update_Migration_array;
    }

}
