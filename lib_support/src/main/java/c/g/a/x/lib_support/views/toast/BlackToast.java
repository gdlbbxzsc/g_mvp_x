package c.g.a.x.lib_support.views.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import c.g.a.x.lib_support.R;


/**
 * Created by Administrator on 2018/4/12.
 */

public class BlackToast {

    public static void showToastLong(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showToastShort(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }


    public static void showToast(Context context, int message, int duration) {
        showToast(context, context.getResources().getText(message), duration);
    }

    public static void showToast(Context context, CharSequence message, int duration) {

        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_black, null);
        //初始化布局控件
        TextView mTextView = toastRoot.findViewById(R.id.textView);
        //为控件设置属性
        mTextView.setText(message);


        //Toast的初始化
        Toast toastStart = new Toast(context);
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 2);
        toastStart.setDuration(duration);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}
