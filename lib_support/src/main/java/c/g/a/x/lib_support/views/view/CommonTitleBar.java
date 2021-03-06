package c.g.a.x.lib_support.views.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import c.g.a.x.lib_support.R;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;


/**
 * Created by Administrator on 2018/4/3.
 */

public class CommonTitleBar {

    private enum TitlebarStyle {
        WHITE(R.color.white, R.drawable.left_arrow_black, R.color.black1, R.color.line),//
        BLACK(R.color.black1, R.drawable.left_arrow_white, R.color.white, R.color.white),//
        TRANSPARENT_BLACK(R.color.touming, R.drawable.left_arrow_black, R.color.black1, R.color.touming),//
        TRANSPARENT_WHITE(R.color.touming, R.drawable.left_arrow_white, R.color.white, R.color.touming);

        private final int bgColor;
        private final int backDrawable;
        private final int textColor;
        private final int lineColor;

        TitlebarStyle(int bgColor, int backDrawable, int textColor, int lineColor) {
            this.bgColor = bgColor;
            this.backDrawable = backDrawable;
            this.textColor = textColor;
            this.lineColor = lineColor;
        }
    }

    private Activity activity;
    private Fragment fragment;
    private final Context context;

    private final ConstraintLayout layout;
    public final ImageButton titlebar_left;
    public final TextView titlebar_center;
    private final View titlebar_bottom_line;

    public View titlebar_right;

    TitlebarStyle style;

    public CommonTitleBar(Activity activity) {
        this.activity = activity;
        context = activity;

        layout = activity.findViewById(R.id.titlebar_bg);
        titlebar_left = activity.findViewById(R.id.titlebar_left);
        titlebar_center = activity.findViewById(R.id.titlebar_center);
        titlebar_bottom_line = activity.findViewById(R.id.titlebar_bottom_line);

        titlebar_left.setOnClickListener(onSingleClickListener);
    }

    public CommonTitleBar(Fragment fragment, View view) {
        this.fragment = fragment;
        context = fragment.getContext();

        layout = view.findViewById(R.id.titlebar_bg);
        titlebar_left = view.findViewById(R.id.titlebar_left);
        titlebar_center = view.findViewById(R.id.titlebar_center);
        titlebar_bottom_line = view.findViewById(R.id.titlebar_bottom_line);

        titlebar_left.setOnClickListener(onSingleClickListener);
    }

    public CommonTitleBar setStyleWhite() {
        return setStyle(TitlebarStyle.WHITE);
    }

    public CommonTitleBar setStyleBlack() {
        return setStyle(TitlebarStyle.BLACK);
    }

    public CommonTitleBar setStyleTransparentWhite() {
        return setStyle(TitlebarStyle.TRANSPARENT_WHITE);
    }

    public CommonTitleBar setStyleTransparentBlack() {
        return setStyle(TitlebarStyle.TRANSPARENT_BLACK);
    }

    public CommonTitleBar setStyle(TitlebarStyle style) {
        this.style = style;

        layout.setBackgroundResource(style.bgColor);
        titlebar_left.setImageResource(style.backDrawable);
        titlebar_center.setTextColor(context.getResources().getColor(style.textColor));
        titlebar_bottom_line.setBackgroundResource(style.lineColor);
        return this;
    }

    public CommonTitleBar setTitle(String title) {
        titlebar_center.setText(title);
        return this;
    }

    public CommonTitleBar setTitle(int titleId) {
        titlebar_center.setText(titleId);
        return this;
    }

    public CommonTitleBar addRightImageButton(int imgId, OnSPClickListener listener) {

        ImageButton ibtn = new ImageButton(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.dp3);
        ibtn.setPadding(padding, padding, padding, padding);
        ibtn.setBackground(new BitmapDrawable());
        ibtn.setImageResource(imgId);
        ibtn.setOnClickListener(listener);

        addItem(ibtn);

        return this;
    }

    public CommonTitleBar addRightTextViewButton(String str, OnSPClickListener listener) {

        TextView tv = new TextView(new ContextThemeWrapper(context, R.style.font_normal1_black1_Btn_B_ww_pad_lr8_tb4));
        tv.setTextColor(style.textColor);
        tv.setOnClickListener(listener);
        tv.setText(str);

        addItem(tv);

        return this;
    }

    private void addItem(View childView) {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        params.rightMargin
                = params.topMargin
                = params.bottomMargin
                = context.getResources().getDimensionPixelSize(R.dimen.dp5);

        params.bottomToBottom
                = params.rightToRight
                = params.topToTop
                = ConstraintLayout.LayoutParams.PARENT_ID;

        layout.addView(childView, layout.getChildCount(), params);

        titlebar_right = childView;
    }


    final OnSPClickListener onSingleClickListener = new OnSPClickListener() {

        @Override
        public void onClickSucc(View v) {
            Logger.e("CommonTitleBar finish");
            if (fragment != null) {
                fragment.getActivity().finish();
                return;
            }
            activity.finish();
        }
    };
}
