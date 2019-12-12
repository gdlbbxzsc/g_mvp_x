package c.g.a.x.lib_db.base.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

import c.g.a.x.lib_db.base.BaseDaoModel;

@Dao
public interface BaseDao<T extends BaseDaoModel> {

    @Insert
    void insert(T vo);//插入单条数据

    @Insert
    void insertList(List<T> list);//插入list数据

    @Delete
    void delete(T vo);//删除item

    @Update
    void update(T vo);//更新item

}
