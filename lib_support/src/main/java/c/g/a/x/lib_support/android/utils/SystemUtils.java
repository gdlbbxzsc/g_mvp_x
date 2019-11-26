package c.g.a.x.lib_support.android.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public final class SystemUtils {


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        if (runningAppProcessInfos == null) {
            return null;
        } else {
            Iterator var4 = runningAppProcessInfos.iterator();

            ActivityManager.RunningAppProcessInfo appProcess;
            do {
                if (!var4.hasNext()) {
                    return null;
                }
                appProcess = (ActivityManager.RunningAppProcessInfo) var4.next();
            } while (appProcess.pid != pid);

            return appProcess.processName;
        }
    }
    public static void ss(){
        try {
            Runtime.getRuntime().exec("input tap " + 90 + " " + 90);// 点击  长按 拖动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}