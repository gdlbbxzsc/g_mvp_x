//package c.g.a.x.module_chat.detail.db;
//
//import androidx.room.Dao;
//import androidx.room.Query;
//
//import com.pbph.module_mqtt.db.BaseDao;
//
//import java.util.List;
//
//
//@Dao
//public interface MsgRecordDao extends BaseDao<MsgRecordVo> {
////查询所有数据
//    @Query("SELECT * FROM MsgRecordVo")
//    List<MsgRecordVo> selectAll();
//
////更新对应id数据的状态
//    @Query("UPDATE MsgRecordVo SET msgState = :msgState WHERE msgId = :msgId")
//    void updateMsgState(String msgId, int msgState);
//
////    将对应id之上旧数据的阅读状态修改
//    @Query("UPDATE MsgRecordVo SET readType = 1 WHERE id < (select id from MsgRecordVo where msgId = :msgId)")
//    void updateReadType(String msgId);
//
//
////    两个 selectPage对应的情况不同查询意图相同
//
////当前界面有数据，则根据最旧一条数据的id 查询数据库中比此id数据更旧的数据集中的最新的50调数据
//    @Query("select * from MsgRecordVo where id<(select id from MsgRecordVo where msgId = :msgId) order by id desc limit 50 offset 0")
//    List<MsgRecordVo> selectPage(String msgId);////offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
////当前界面无数据时，查询数据库中最新的50条数据
//    @Query("select * from MsgRecordVo order by id desc limit 50 offset 0")
//    List<MsgRecordVo> selectPage();////offset代表从第几条记录“之后“开始查询，limit表明查询多少条结果
//
////保留最新500条数据删除更旧的全部数据
//    @Query("DELETE from MsgRecordVo where id not in(select id from MsgRecordVo order by id desc limit 500 offset 0)")
//    void deleteOlds();
//
//
//}
