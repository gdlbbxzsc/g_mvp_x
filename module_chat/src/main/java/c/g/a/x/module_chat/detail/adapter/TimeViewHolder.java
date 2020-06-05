package c.g.a.x.module_chat.detail.adapter;

import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatTimeBinding;

//这里应该根据两条数据间的时间差和 当前系统时间间隔展示不同的时间
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
