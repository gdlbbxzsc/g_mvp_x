package c.g.a.x.lib_support.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import c.g.a.x.lib_support.R;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;


/**
 * Created by zyp on 2019/2/18.
 * note:加入战队弹出框
 */

public class ShareDialog extends Dialog {


    public static ShareDialog show(Context context, Dialog.OnClickListener onClickListener) {
        ShareDialog dialog = new ShareDialog(context, onClickListener);
        dialog.show();
        return dialog;
    }


    private ShareDialog(Context context, Dialog.OnClickListener onClickListener) {
        super(context, R.style.ShareDialogTheme);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share, null);

        TextView tv_share_life = rootView.findViewById(R.id.tv_share_life);
        OnSPClickListener listener = new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                int id = v.getId();
                if (id == R.id.tv_share_life) {
                    onClickListener.onClick(ShareDialog.this, 1);
                } else if (id == R.id.tv_share_timeline) {
                    onClickListener.onClick(ShareDialog.this, 2);
                } else if (id == R.id.tv_share_qq) {
                    onClickListener.onClick(ShareDialog.this, 3);
                } else if (id == R.id.btn_share_esc) {
                    onClickListener.onClick(ShareDialog.this, 0);
                }
                dismiss();
            }
        };
        tv_share_life.setOnClickListener(listener);

        TextView tv_share_timeline = rootView.findViewById(R.id.tv_share_timeline);
        tv_share_timeline.setOnClickListener(listener);

        TextView tv_share_qq = rootView.findViewById(R.id.tv_share_qq);
        tv_share_qq.setOnClickListener(listener);

        Button btn_share_submit = rootView.findViewById(R.id.btn_share_esc);
        btn_share_submit.setOnClickListener(listener);
        setContentView(rootView);
    }


    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.getDecorView().setPadding(0, 0, 0, 0);

        window.setAttributes(lp);
    }
}
