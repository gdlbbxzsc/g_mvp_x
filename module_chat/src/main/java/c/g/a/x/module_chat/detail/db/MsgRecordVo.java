//package c.g.a.x.module_chat.detail.db;
//
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import com.pbph.module_mqtt.db.BaseDaoModel;
//
//
///**
// * Created by Administrator on 2018/3/7.
// */
//
//
//@Entity()
//public class MsgRecordVo extends BaseDaoModel {
//
//    @PrimaryKey(autoGenerate = true)
//    public int id;
//
//    @ColumnInfo()
//    public int msgState; // 0-sending | 1-success | 2-fail
//
//    @ColumnInfo()
//    public int messageType; //消息类型（1普通消息 2 图片消息 3 系统消息）
//    @ColumnInfo()
//    public String source;
//    @ColumnInfo()
//    public String target;
//    @ColumnInfo()
//    public long createTime;
//    @ColumnInfo()
//    public int readType;
//    @ColumnInfo()
//    public String content;
//    @ColumnInfo()
//    public String msgId;
//
//}
