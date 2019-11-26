package c.g.a.x.lib_db.roomTest;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import c.g.a.x.lib_db.base.BaseDaoModel;

/**
 * Created by Administrator on 2018/3/7.
 */

@Entity()
public class RoomTest extends BaseDaoModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo()
    public String message;
    @ColumnInfo()
    public String info;

}
