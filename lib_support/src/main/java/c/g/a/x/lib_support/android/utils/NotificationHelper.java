package c.g.a.x.lib_support.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;


/**
 * Created by Administrator on 2018/11/14.
 */

public final class NotificationHelper {

    public static void checkEnabledDialog(Context context) {
        if (checkEnabled(context)) return;
        createDialog(context).show();
    }

    public static boolean checkEnabled(Context context) {
        boolean isOpened = NotificationManagerCompat.from(context).areNotificationsEnabled();
        Logger.e(NotificationHelper.class.getName(), "===>", isOpened);
        return isOpened;
    }

    public static Dialog createDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("通知权限");
        builder.setMessage("需要获取通知栏权限，是否设置？");
        builder.setNegativeButton("否", null);
        builder.setPositiveButton("是", (dialogInterface, i) -> {
            String name = context.getPackageName();
            Logger.e(NotificationHelper.class.getName(), "===>", name);

            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", name, null);
            intent.setData(uri);
            context.startActivity(intent);
        });
        return builder.create();
    }
}
