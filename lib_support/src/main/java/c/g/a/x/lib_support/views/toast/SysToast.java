package c.g.a.x.lib_support.views.toast;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by Administrator on 2018/4/12.
 */

public class SysToast {

    public static void showToastLong(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showToastShort(Context context, CharSequence message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }
 
    public static void showToast(Context context, int message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showToast(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
