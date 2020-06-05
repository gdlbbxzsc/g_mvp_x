package c.g.a.x.module_chat.detail.adapter;

import android.view.View;

import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatRecTextBinding;
import c.g.a.x.module_chat.detail.bean.ChatMsg;

public class RecTextViewHolder extends ViewHolder<ChatMsg, AdapterChatRecTextBinding> {

    @Override
    protected int getLayout() {
        return R.layout.adapter_chat_rec_text;
    }

    @Override
    protected void getView() {
        binder.chatItemProgress.setVisibility(View.GONE);
        binder.chatItemFail.setVisibility(View.GONE);
        binder.chatItemRead.setVisibility(View.GONE);
    }

    @Override
    protected void showView() {
        ChatMsg.BodyBean body = item.getBody();
        binder.chatItemContentText.setText(body.getContent());
    }


}
