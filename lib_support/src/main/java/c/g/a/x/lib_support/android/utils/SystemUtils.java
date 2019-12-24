package c.g.a.x.lib_support.android.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    public static void exec() {
        try {
            Runtime.getRuntime().exec("input tap " + 90 + " " + 90);// 点击  长按 拖动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static CommandResult exec(boolean isRoot, List<String> commands) {
        return exec(isRoot, commands.toArray(new String[]{}));
    }

    public static CommandResult exec(boolean isRoot, String... commands) {

        Process process = null;

        DataOutputStream os = null;

        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");

            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();

            int result = process.waitFor();

            String s;

            StringBuilder successMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            StringBuilder errorMsg = new StringBuilder();
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }

            return new CommandResult(result, successMsg.toString(), errorMsg.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new CommandResult(-1, null, null);
        } finally {
            if (errorResult != null) {
                try {
                    errorResult.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (successResult != null) {
                try {
                    successResult.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }

    }

    //返回的命令结果
    public static final class CommandResult {
        public int result; // 结果码

        public String successMsg;//成功信息

        public String errorMsg;//错误信息

        CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}