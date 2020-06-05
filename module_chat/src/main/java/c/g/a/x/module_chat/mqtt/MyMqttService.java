package c.g.a.x.module_chat.mqtt;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;

import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.module_chat.detail.bean.ChatMsg;

//被注释掉的部分是数据库压数据以及界面更新逻辑 所有readtype的判断都是如下逻辑
//为0代表两端主动发送的信息。为1代表两端收到数据后自动应答的 您发送的消息我已阅读 的状态消息
public class MyMqttService extends Service {

    private final String HOST_PRE = "tcp://";//服务器地址（协议+地址+端口号）

    private MqttAndroidClient mqttAndroidClient;

    private LocalBinder binder = new LocalBinder();

    private Map<IMqttToken, ChatMsg> map = new HashMap<>(10, 1);

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        try {
            if (mqttAndroidClient != null && mqttAndroidClient.isConnected()) {
                mqttAndroidClient.disconnect();
                mqttAndroidClient.unregisterResources();
//                mqttAndroidClient.close();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private MqttConnectOptions initMqttConnectOptions() {
        MQTTConfigInfo configInfo = MQTTConfigInfo.getInstance();

        MqttConnectOptions mMqttConnectOptions = new MqttConnectOptions();
        mMqttConnectOptions.setAutomaticReconnect(true);     //设置自动重连
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        //MqttCallbackExtended connectComplete中重新订阅与之相关
        mMqttConnectOptions.setCleanSession(true);
        mMqttConnectOptions.setConnectionTimeout(10); //设置超时时间，单位：秒
        mMqttConnectOptions.setKeepAliveInterval(20); //设置心跳包发送间隔，单位：秒
        mMqttConnectOptions.setUserName(configInfo.username); //设置用户名
        mMqttConnectOptions.setPassword(configInfo.password.toCharArray()); //设置密码


        // 遗嘱 当本机异常断开连接后 会发送这条消息，其他所有订阅者都会收到这条消息
        String message = "{\"close_cid\":\"" + configInfo.clientId + "\"}";
        mMqttConnectOptions.setWill(configInfo.serverTopic, message.getBytes(), 2, false);

        return mMqttConnectOptions;
    }

    private DisconnectedBufferOptions initDisconnectedBufferOptions() {
        /*连接成功之后设置连接断开的缓冲配置*/
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        //开启
        disconnectedBufferOptions.setBufferEnabled(false);
        //离线后最多缓存100调
        disconnectedBufferOptions.setBufferSize(100);
        //不一直持续留存
        disconnectedBufferOptions.setPersistBuffer(false);
        //删除旧消息
        disconnectedBufferOptions.setDeleteOldestMessages(false);

        return disconnectedBufferOptions;
    }

    /**
     * 初始化
     */
    private void init() {
        MQTTConfigInfo configInfo = MQTTConfigInfo.getInstance();
        String url = HOST_PRE + configInfo.mqttUrl + ":" + configInfo.tcpPort;
        mqttAndroidClient = new MqttAndroidClient(this, url, configInfo.clientId);
        //设置监听订阅消息的回调
        mqttAndroidClient.setCallback(commonMqttCallbackExtended);

//        设置连接参数
        MqttConnectOptions mMqttConnectOptions = initMqttConnectOptions();

        connect(mMqttConnectOptions);
    }

    private void connect(MqttConnectOptions mMqttConnectOptions) {
        try {
            mqttAndroidClient.connect(mMqttConnectOptions, null, connectIMqttActionListener);
        } catch (MqttException ex) {
            ex.printStackTrace();
            optionsHistory("connect error");
        }
    }

    //开始订阅
    public void subscribe() {
        try {

            mqttAndroidClient.subscribe(MQTTConfigInfo.getInstance().topicSelf, 2, null, subscribeIMqttActionListener);
        } catch (MqttException ex) {
            ex.printStackTrace();
            optionsHistory("subscribe error");
        }
    }

    //开始订阅
    public void unsubscribe() {
        try {
            mqttAndroidClient.unsubscribe(MQTTConfigInfo.getInstance().topicSelf, null, unsubscribeIMqttActionListener);
        } catch (MqttException ex) {
            ex.printStackTrace();
            optionsHistory("subscribe error");
        }
    }

    //发布消息
    public void publish(ChatMsg msg) {
        try {
////将客户端发送的数据存入到数据库中去
            ////判断为0表示磁条数据为用户自己发送的消息
            // //为1是用户客户端收到服务器消息时自动回复的已读消息
            // //这里逻辑重点在于将用户自己的消息存入数据库 其他的都可以修改
//            if (msg.getBody().getReadType() == 0)
//                DBMnger.getInstance(MyMqttService.this).msgRecordDao().insert(Message.createMsgRecordVo(msg));


//            /*发送，使用MqttMessage封装类来进行发送*/
//            MqttMessage message = new MqttMessage(str.getBytes());
//            //设置优先级
//            message.setQos(2);
//            //设置是否被服务器保留
//            message.setRetained(true);


            msg.msgState = ChatMsg.MSG_STATE_SENDING;//默认为发送中

            //使用mqttClient进行发送到对应主题
            IMqttDeliveryToken token = mqttAndroidClient.publish(MQTTConfigInfo.getInstance().serverTopic, msg.toJson().getBytes(), 2,
                    true, null, publishIMqttActionListener);
//将用户发送的消息缓存一下 下边会有消息发送回调机制去处理消息状态
            map.put(token, msg);

        } catch (MqttException e) {
            e.printStackTrace();
            optionsHistory("publish error");
        }
    }


    //通用回调
    MqttCallbackExtended commonMqttCallbackExtended = new MqttCallbackExtended() {

        /**
         * 连接完成回调
         * @param reconnect true 断开重连,false 首次连接
         * @param serverURI 服务器URI
         */
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            optionsHistory("connectComplete to:" + serverURI + " reconnect:" + reconnect);
            //订阅主题
            // Because Clean Session is true, we need to re-subscribe
            subscribe();
        }

        /**
         * @desc 连接断开回调
         * 可在这里做一些重连等操作
         */
        @Override
        public void connectionLost(Throwable arg0) {
            arg0.printStackTrace();
            optionsHistory("connectionLost");
        }

        /**
         * 消息接收，如果在订阅的时候没有设置IMqttMessageListener，那么收到消息则会在这里回调。
         * 如果设置了IMqttMessageListener，则消息回调在IMqttMessageListener中
         * @param topic 该消息来自的订阅主题
         * @param message 消息内容
         */
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            optionsHistory("messageArrived:", topic, message);

            try {
                String temp = message.toString();
                ChatMsg msg = new Gson().fromJson(temp, ChatMsg.class);//把JSON字符串转为对象

                ChatMsg.BodyBean body = msg.getBody();
//如果为0代表服务器主动发送的自主消息 为1则为服务器收到客户端消息自动回复的已读消息
//                if (body.getReadType() == 0) {
                //将服务器发来的消息存入数据库
//                    int rrr = new java.util.Random().nextInt(900) + 100;
//                    body.setMsgId(body.getCreateTime() + "" + rrr);
//                    DBMnger.getInstance(MyMqttService.this).msgRecordDao().insert(Message.createMsgRecordVo(msg));
//                } else {
//                如果服务在线并且阅读了客户端发送的消息会主动发送消息 已读消息 客户端根据此数据对自己发送的消息列表更新一下已读未读状态
//                    DBMnger.getInstance(MyMqttService.this).msgRecordDao().updateReadType(msg.getBody().getMsgId());
//                }
//服务器消息默认为已成功，因为能收到就代表已成功
//                msg.msgState = Message.MSG_STATE_SUCCESS;
//                将服务器消息发送出去让对应的消息展示界面进行处理展示
//                RxBus.post0(msg);

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        /**
         * 在publish消息的时候会收到此回调完成回调。在publish消息的时候会收到此回调.
         * qos:
         * 0 发送完则回调
         * 1 或 2 会在对方收到时候回调
         * @param token
         */
        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            optionsHistory("deliveryComplete:", token);
        }
    };

    //    连接成功
    IMqttActionListener connectIMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            optionsHistory("connect onSuccess:", asyncActionToken);
            DisconnectedBufferOptions disconnectedBufferOptions = initDisconnectedBufferOptions();
            if (disconnectedBufferOptions != null)
                mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            exception.printStackTrace();
            optionsHistory("connect onFailure:", asyncActionToken);
        }
    };
    //    订阅成功
    IMqttActionListener subscribeIMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            optionsHistory("subscribe onSuccess:", asyncActionToken);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            exception.printStackTrace();
            optionsHistory("subscribe onFailure:", asyncActionToken);
        }
    };
    //    取消订阅成功
    IMqttActionListener unsubscribeIMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            optionsHistory("unsubscribe onSuccess:", asyncActionToken);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            exception.printStackTrace();
            optionsHistory("unsubscribe onFailure:", asyncActionToken);
        }
    };

    //    发布成功
    IMqttActionListener publishIMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
//找到对应的消息更改消息的状态为成功，这里因为是引用传递所以改了以后所有使用的地方使用的是同一个对象，所以值也被改变了，只要通知对应界面刷新即可
//            如果不是这样原理的那么就要根据
//                    IMqttDeliveryToken temp= (IMqttDeliveryToken) asyncActionToken;
//                    temp.getMessage().getPayload()//发送的数据
//            这个逻辑找到之前发送的数据并根据数据的主键 uuid 或者是其他什么去修改数据状态

//            找到对应的数据
            ChatMsg msg = map.get(asyncActionToken);
            if (msg == null) return;

            map.remove(asyncActionToken);

//            修改数据状态
            msg.msgState = ChatMsg.MSG_STATE_SUCCESS;


            ChatMsg.BodyBean body = msg.getBody();
//            将数据库中的数据状态也刷新一下，为0为1同上边一样，0代表自己发送的数据1代表客户端自动应答的已读数据没什么用
//            if (body.getReadType() == 0)
//                DBMnger.getInstance(MyMqttService.this).msgRecordDao().updateMsgState(body.getMsgId(), msg.msgState);

            Logger.e("publish succ:", msg.toJson());
//            将数据广播到其他界面进行相关界面刷新
//            RxBus.post0(msg);

            optionsHistory("publish onSuccess:", asyncActionToken);
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            //逻辑同上 状态改为失败
            ChatMsg msg = map.get(asyncActionToken);
            if (msg == null) return;

            map.remove(asyncActionToken);

            msg.msgState = ChatMsg.MSG_STATE_FAIL;

            ChatMsg.BodyBean body = msg.getBody();
//            if (body.getReadType() == 0)
//                DBMnger.getInstance(MyMqttService.this).msgRecordDao().updateMsgState(body.getMsgId(), msg.msgState);

//            RxBus.post0(msg);

            exception.printStackTrace();
            optionsHistory("publish onFailure:", asyncActionToken);
        }
    };


    //操作记录
    private void optionsHistory(String preTag, String topic, MqttMessage message) {
        optionsHistory(preTag + topic + " message:" + message.toString());
    }

    private void optionsHistory(String preTag, IMqttDeliveryToken iMqttDeliveryToken) {
        optionsHistory(preTag + iMqttDeliveryToken.toString());
    }

    private void optionsHistory(String preTag, IMqttToken asyncActionToken) {
        optionsHistory(preTag + asyncActionToken.toString());
    }

    private void optionsHistory(String str) {
        Log.e("==>", str);
    }

    /**
     * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口
     */
    public class LocalBinder extends Binder {
        // 声明一个方法，getService。（提供给客户端调用）
        MyMqttService getService() {
            // 返回当前对象LocalService,这样我们就可在客户端端调用Service的公共方法了
            return MyMqttService.this;
        }
    }


}
