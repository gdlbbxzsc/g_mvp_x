package c.g.a.x.lib_db.roomTest;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import c.g.a.x.lib_db.base.room.BaseDao;

@Dao
public interface RoomTestDao extends BaseDao<RoomTest> {

    @Query("SELECT * FROM RoomTest")
    List<RoomTest> getAll();

    @Query("SELECT * FROM RoomTest WHERE id IN (:ids)")
    List<RoomTest> loadAllByIds(int[] ids);

    @Query("SELECT * FROM RoomTest WHERE :message LIKE :message")
    RoomTest findByMessage(String message);
}
