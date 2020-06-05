package c.g.a.x.module_chat.detail.adapter;

import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatRecSysBinding;
import c.g.a.x.module_chat.detail.bean.ChatMsg;

public class RecSysViewHolder extends ViewHolder<ChatMsg, AdapterChatRecSysBinding> {

    @Override
    protected int getLayout() {
        return R.layout.adapter_chat_rec_sys;
    }


    @Override
    protected void showView() {
        ChatMsg.BodyBean body = item.getBody();
        binder.chatItemContentText.setText(body.getContent());
    }

}
