package c.g.a.x.module_chat.detail.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatRecTextBinding;
import c.g.a.x.module_chat.databinding.AdapterChatSendTextBinding;
import c.g.a.x.module_chat.detail.bean.ChatMsg;
import io.github.rockerhieu.emojicon.EmojiconTextView;


public class SendTextViewHolder extends ViewHolder<ChatMsg, AdapterChatSendTextBinding> {



    @Override
    protected int getLayout() {
        return R.layout.adapter_chat_send_text;
    }

    @Override
    protected void getView( ) {
        binder.chatItemProgress.setVisibility(View.GONE);
        binder.chatItemFail.setVisibility(View.GONE);
        binder.chatItemRead.setVisibility(View.GONE);
    }

    @Override
    protected void showView() {

        ChatMsg.BodyBean body = item.getBody();
        binder.chatItemContentText.setText(body.getContent());

        //消息发送的状态
        switch (item.msgState) {
            case ChatMsg.MSG_STATE_SENDING: {
                binder.chatItemProgress.setVisibility(View.VISIBLE);
                binder.chatItemFail.setVisibility(View.GONE);

            }
            break;
            case ChatMsg.MSG_STATE_SUCCESS: {
                binder.chatItemProgress.setVisibility(View.GONE);
                binder.chatItemFail.setVisibility(View.GONE);
            }
            break;
            case ChatMsg.MSG_STATE_FAIL: {
                binder.chatItemProgress.setVisibility(View.GONE);
                binder.chatItemFail.setVisibility(View.VISIBLE);
            }
            break;
        }
//        （0 未读 1 已读）
        if (body.getReadType() == 0 && item.msgState == ChatMsg.MSG_STATE_SUCCESS)
            binder.chatItemRead.setVisibility(View.VISIBLE);
        else
            binder.chatItemRead.setVisibility(View.GONE);
    }


}
