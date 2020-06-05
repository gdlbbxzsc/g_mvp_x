package c.g.a.x.module_chat.mqtt;

//对应的topic 管理
public final class MQTTConfigInfo {

    public static MQTTConfigInfo getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final MQTTConfigInfo INSTANCE = new MQTTConfigInfo();
    }

    public String username;
    public String userType;
    public String password;
    public String clientId;
    //    具体逻辑具体分析
    public String topicSelf;//订阅用topic
    public String serverTopic;//发送用topic
    public String targetTopic;
    public String targetClientId;
    public String websocketUrl;

    public String mqttUrl;//服务器发给客户端连接mqtt的地址
    public String tcpPort;

//    public final void setData(GetClientParamResponse.DataBean data) {
//        clientId = data.getClientId();
//
//        mqttUrl = data.getMqttUrl();
//        tcpPort = data.getTcpPort();
//
//        username = data.getUsername();
//        password = data.getPassword();
//
//        topicSelf = data.getTopicSelf();
//        serverTopic = data.getServerTopic();
//
//        targetTopic = data.getTargetTopic();
//
//    }

    public final void clear() {
        mqttUrl = null;
        tcpPort = null;

        username = null;
        password = null;

        topicSelf = null;
        serverTopic = null;

        targetTopic = null;
    }
}
