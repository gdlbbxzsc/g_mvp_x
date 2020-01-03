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
        return toast(context, v);
    }

    public static MyDialog toast(Context context, View toastView) {
        return builder(context)
                .setContentView(toastView)
                .setPositiveButton()
                .show();
    }

    public static MyDialog confirm(Context context, String toast, OnClickListener onClickListener) {
        TextView v = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_dialog_toast_text, null);
        v.setText(toast);
        return confirm(context, v, onClickListener);
    }

    public static MyDialog confirm(Context context, View toastView, OnClickListener onClickListener) {
        return builder(context)
                .setContentView(toastView)
                .setPositiveButton()
                .setNegativeButton()
                .setOnClickListener(onClickListener)
                .cancelable(false)
                .show();
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    private MyDialog(Context context, Builder builder) {
        super(context, R.style.DialogTheme);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(builder.cancelable);

        int dp20 = (int) context.getResources().getDimension(R.dimen.dp20);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout rootView = new LinearLayout(context);
        rootView.setGravity(Gravity.CENTER);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setPadding(dp20, dp20, dp20, dp20);
        rootView.setBackgroundResource(R.drawable.default_dialog_bg);

        //
        TextView title = (TextView) inflater.inflate(R.layout.dialog_common_top_title, null);
        if (!StringUtils.isEmpty(builder.title)) title.setText(builder.title);
        if (builder.titleColor != null)
            title.setTextColor(getContext().getResources().getColor(builder.titleColor));
        rootView.addView(title);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title.getLayoutParams();
        params.bottomMargin = dp20;
        title.setLayoutParams(params);
//
        rootView.addView(builder.contentView);
//
        LinearLayout bottom_buttons = new LinearLayout(context);
        rootView.setGravity(Gravity.CENTER);
        rootView.setOrientation(LinearLayout.HORIZONTAL);
        rootView.addView(bottom_buttons);
        params = (LinearLayout.LayoutParams) bottom_buttons.getLayoutParams();
        params.topMargin = dp20;
        bottom_buttons.setLayoutParams(params);

        if (builder.positiveButton)
            addButton(inflater, bottom_buttons, R.layout.dialog_common_bottom_button_yes, builder.positiveButtonText, builder.onClickListener, WhichButton.Yes);

        if (builder.negativeButton)
            addButton(inflater, bottom_buttons, R.layout.dialog_common_bottom_button_no, builder.negativeButtonText, builder.onClickListener, WhichButton.No);

        setOnCancelListener(dialog -> {
            if (builder.onClickListener != null)
                builder.onClickListener.onClick(dialog, WhichButton.Esc);
        });
        setOnDismissListener(dialog -> {
            if (builder.listenerDismiss)
                builder.onClickListener.onClick(dialog, WhichButton.Dismiss);
        });
        setContentView(rootView);
    }

    private void addButton(LayoutInflater inflater, LinearLayout bottom_buttons, int buttonLayoutId, String text, OnClickListener listener, WhichButton which) {
        TextView button = (TextView) inflater.inflate(buttonLayoutId, null);

        if (!StringUtils.isEmpty(text)) button.setText(text);

        button.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                boolean b = true;
                if (listener != null) b = listener.onClick(MyDialog.this, which);
                if (b) dismiss();
            }
        });

        bottom_buttons.addView(button);

        LinearLayout.LayoutParams paramsPos = (LinearLayout.LayoutParams) button.getLayoutParams();
        paramsPos.weight = 1;
        button.setLayoutParams(paramsPos);
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

        private final Context context;

        private String title;
        private Integer titleColor;

        private View contentView;

        private boolean negativeButton;
        private String negativeButtonText;
        private boolean positiveButton = true;
        private String positiveButtonText;

        private boolean cancelable = true;
        private boolean listenerDismiss;

        private OnClickListener onClickListener;

        private Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleColor(int color) {
            this.titleColor = color;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return setNegativeButton();
        }

        public Builder setNegativeButton() {
            this.negativeButton = true;
            return this;
        }


        public Builder setPositiveButton(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return setPositiveButton();
        }

        public Builder setPositiveButton() {
            this.positiveButton = true;
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

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder listenerDismiss() {
            this.listenerDismiss = true;
            return this;
        }

        public final MyDialog show() {
            MyDialog dialog = new MyDialog(context, this);
            dialog.show();
            return dialog;
        }
    }

    private enum WhichButton {
        Yes, No, Esc, Dismiss
    }

    public interface OnClickListener {
        boolean onClick(DialogInterface dialog, WhichButton which);
    }

}
