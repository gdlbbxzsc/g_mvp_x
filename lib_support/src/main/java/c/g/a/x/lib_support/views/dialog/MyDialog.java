package c.g.a.x.lib_support.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import c.g.a.x.lib_support.R;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.utils.StringUtils;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;


/**
 * Created by Administrator on 2018/3/12.
 */

public final class MyDialog extends Dialog {


    public static MyDialog toast(Context context, String toast) {
        TextView v = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_dialog_toast_text, null);
        v.setText(toast);
        return builder(context).setButtonStyleOne().setContentView(v).show();
    }

    public static MyDialog confirm(Context context, String toast, OnClickListener onClickListener) {
        TextView v = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_dialog_toast_text, null);
        v.setText(toast);
        return builder(context).setButtonStyleTwo().setContentView(v).setOnClickListener(onClickListener).show();
    }

    public static MyDialog toast(Context context, View toastView) {
        return builder(context).setButtonStyleOne().setContentView(toastView).show();
    }

    public static MyDialog confirm(Context context, View toastView, OnClickListener onClickListener) {
        return builder(context).setButtonStyleTwo().setContentView(toastView).setOnClickListener(onClickListener).show();
    }

    public static final Builder builder(Context context) {
        return new Builder(context);
    }

    private MyDialog(Context context, Builder builder) {
        super(context, R.style.DialogTheme);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int dp20 = (int) context.getResources().getDimension(R.dimen.dp20);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout rootView = new LinearLayout(context);
        rootView.setGravity(Gravity.CENTER);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setPadding(dp20, dp20, dp20, dp20);
        rootView.setBackgroundResource(R.drawable.default_dialog_bg);

        TextView title = (TextView) inflater.inflate(R.layout.dialog_custom_top_title, null);
        if (!StringUtils.isEmpty(builder.title)) title.setText(builder.title);
        rootView.addView(title);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title.getLayoutParams();
        params.bottomMargin = dp20;
        title.setLayoutParams(params);

        rootView.addView(builder.contentView);

        View bottom_buttons;
        TextView negativeButton = null;
        switch (builder.buttonStyle) {
            case style_two: {
                bottom_buttons = inflater.inflate(R.layout.dialog_custom_bottom_button_two, null);
                negativeButton = bottom_buttons.findViewById(R.id.btn_dialog_no);
            }
            break;
            default: {
                bottom_buttons = inflater.inflate(R.layout.dialog_custom_bottom_button_one, null);
            }
        }
        TextView positiveButton = bottom_buttons.findViewById(R.id.btn_dialog_yes);

        rootView.addView(bottom_buttons);
        params = (LinearLayout.LayoutParams) bottom_buttons.getLayoutParams();
        params.topMargin = dp20;
        bottom_buttons.setLayoutParams(params);

        if (negativeButton != null) {
            if (!StringUtils.isEmpty(builder.negativeButtonText))
                negativeButton.setText(builder.negativeButtonText);
            negativeButton.setOnClickListener(new OnSPClickListener() {

                @Override
                public void onClickSucc(View v) {
                    boolean b = true;
                    if (builder.onClickListener != null)
                        b = builder.onClickListener.onClick(MyDialog.this, 0);
                    if (b) dismiss();
                }
            });
        }

        if (!StringUtils.isEmpty(builder.positiveButtonText))
            positiveButton.setText(builder.positiveButtonText);
        positiveButton.setOnClickListener(new OnSPClickListener() {

            @Override
            public void onClickSucc(View v) {
                boolean b = true;
                if (builder.onClickListener != null)
                    b = builder.onClickListener.onClick(MyDialog.this, 1);
                if (b) dismiss();
            }
        });

        setContentView(rootView);
    }

    @Override
    public void show() {
        super.show();
//        //        /////////获取屏幕宽度
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
        /////////设置高宽
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (AndroidUtils.getWidthInPx(getContext()) * 0.75); // 宽度
//        lp.height = (int) (lp.width * 0.60);     // 高度
        dialogWindow.setAttributes(lp);
    }


    public static final class Builder {

        private Context context;

        private String title;

        private ButtonStyle buttonStyle;
        private String negativeButtonText;
        private String positiveButtonText;

        private OnClickListener onClickListener;

        private View contentView;

        private Builder(Context context) {
            this.context = context;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setButtonStyleOne() {
            this.buttonStyle = ButtonStyle.style_one;
            return this;
        }

        public Builder setButtonStyleTwo() {
            this.buttonStyle = ButtonStyle.style_two;
            return this;
        }


        public Builder setNegativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }


        public Builder setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }


        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public final MyDialog show(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return show();
        }

        public final MyDialog show() {
            MyDialog dialog = new MyDialog(context, this);
            dialog.show();
            return dialog;
        }
    }

    private enum ButtonStyle {
        style_one, style_two
    }

    public interface OnClickListener {
        boolean onClick(DialogInterface dialog, int which);
    }

}
