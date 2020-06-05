package c.g.a.x.module_chat.detail.adapter;

import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatTimeBinding;


public class TimeViewHolder extends ViewHolder<String, AdapterChatTimeBinding> {


    @Override
    protected int getLayout() {
        return R.layout.adapter_chat_time;
    }


    @Override
    protected void showView() {
        binder.chatItemDate.setText(item);
    }


}
