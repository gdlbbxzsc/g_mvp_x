package c.g.a.x.module_chat.detail.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import c.g.a.x.lib_support.views.adapter.v2.abslistview.ViewHolder;
import c.g.a.x.module_chat.R;
import c.g.a.x.module_chat.databinding.AdapterChatRecImgBinding;
import c.g.a.x.module_chat.detail.bean.ChatMsg;

public class RecImgViewHolder extends ViewHolder<ChatMsg, AdapterChatRecImgBinding> {

    @Override
    protected int getLayout() {
        return R.layout.adapter_chat_rec_img;
    }

    @Override
    protected void getView() {

        ChatDetailAdapter chatDetailAdapter = (ChatDetailAdapter) adapter;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binder.chatItemContentImage.getLayoutParams();
        params.width = chatDetailAdapter.imgw;
        binder.chatItemContentImage.setLayoutParams(params);

        binder.chatItemProgress.setVisibility(View.GONE);
        binder.chatItemFail.setVisibility(View.GONE);
        binder.chatItemRead.setVisibility(View.GONE);
    }

    @Override
    protected void showView() {
        ChatMsg.BodyBean body = item.getBody();

        Glide.with(adapter.context).load(body.getContent()).into(binder.chatItemContentImage);
    }


}
