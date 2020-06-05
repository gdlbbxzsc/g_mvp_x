
package c.g.a.x.module_chat.detail.adapter;

import android.content.Context;
import android.widget.AbsListView;


import java.lang.reflect.ParameterizedType;

import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.views.adapter.v2.abslistview.DataAdapter;
import c.g.a.x.module_chat.detail.bean.ChatMsg;

public class ChatDetailAdapter extends DataAdapter {
    public int imgw = 0;

    public ChatDetailAdapter(Context context, AbsListView view) {
        super(context, view,
                TimeViewHolder.class,
                RecImgViewHolder.class,
                RecTextViewHolder.class,
                SendImgViewHolder.class,
                SendTextViewHolder.class,
                RecSysViewHolder.class);

        imgw = (int) (AndroidUtils.getWidthInPx(context) / 3);
    }


    protected String getDataType(Class clz) {
        if (clz == SendTextViewHolder.class) {
            return "SendTextViewHolder";
        }
        if (clz == SendImgViewHolder.class) {
            return "SendImgViewHolder";
        }
        if (clz == RecTextViewHolder.class) {
            return "RecTextViewHolder";
        }
        if (clz == RecImgViewHolder.class) {
            return "RecImgViewHolder";
        }
        if (clz == RecSysViewHolder.class) {
            return "RecSysViewHolder";
        }

        return ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments()[0].toString();
    }

    protected String getDataType(int position) {
        Object obj = getItem(position);

        if (obj instanceof ChatMsg) {
            ChatMsg msg = (ChatMsg) obj;
            if (msg.getMessageType() == 3) return "RecSysViewHolder";


            if (msg.getSource().equals("客户端")) {
                if (msg.getMessageType() == 1) return "SendTextViewHolder";
                if (msg.getMessageType() == 2) return "SendImgViewHolder";
            }
            if (msg.getSource().equals("服务端")) {
                if (msg.getMessageType() == 1) return "RecTextViewHolder";
                if (msg.getMessageType() == 2) return "RecImgViewHolder";
            }
        }

        return getItem(position).getClass().toString();
    }
}
