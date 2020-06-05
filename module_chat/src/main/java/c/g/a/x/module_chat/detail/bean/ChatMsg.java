/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package c.g.a.x.module_chat.detail.bean;

import com.google.gson.Gson;

import java.util.Date;

/**
 * 聊天消息javabean
 *
 * @author kymjs (http://www.kymjs.com/)
 */
public class ChatMsg {

    /**
     * body : {"createTime":" 2020 - 05 - 20 T14: 15: 35.316 Z ","readType":1,"content":"最后阅读时间"}
     * messageType : 0
     * source :  /testk8s/mqttOrdinaryClient / 04 dde1389f436a5c341e0343c9b62aab08770ffb0570bfb00e2278e1df26783d
     * target :  /testk8s/mqttCustomerServerClient / 87295e b1be40ba881b0c2531ee6394e60a4fdda4ae4f91fd46b54673b761847e
     */

    private BodyBean body;
    private int messageType; //消息类型（1普通消息 2 图片消息 3 系统消息）
    private String source;
    private String target;

    public int msgState; // 0-sending | 1-success | 2-fail


    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public static class BodyBean {
        /**
         * createTime :  2020 - 05 - 20 T14: 15: 35.316 Z
         * readType : 1
         * content : 最后阅读时间
         */

        private long createTime;
        private int readType;//阅读状态 0未读1已读
        private String content;
        private String msgId;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getReadType() {
            return readType;
        }

        public void setReadType(int readType) {
            this.readType = readType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }


    public static final int MSG_STATE_SENDING = 0;
    public static final int MSG_STATE_SUCCESS = 1;
    public static final int MSG_STATE_FAIL = 2;


    public static final ChatMsg createSendMsg(String content, int type) {
        ChatMsg message = new ChatMsg();
        message.msgState = ChatMsg.MSG_STATE_SENDING;

        message.setMessageType(type);

        ChatMsg.BodyBean body = new ChatMsg.BodyBean();
        long tt = new Date().getTime();
        int rrr = new java.util.Random().nextInt(900) + 100;
//msgid 相当于主键 其实应该由服务器返回，这样能统一由服务器管理消息，避免消息错乱重复等
        body.setMsgId(tt + "" + rrr);
        body.setReadType(0);
        body.setContent(content);
        body.setCreateTime(tt);//时间也应当由服务器返回,这样能避免客户端修改自己终端时间 造成时间错误


        message.setBody(body);

        return message;
    }


}
