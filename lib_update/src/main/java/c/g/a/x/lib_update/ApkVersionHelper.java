package c.g.a.x.lib_update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.utils.StringUtils;


/**
 * Created by Administrator on 2018/7/24.
 */

public final class ApkVersionHelper {

    private Context context;

    private InstallType installType = InstallType.Dialog;
    private boolean mustInstall;

    private Integer versionCode;
    private String versionName;
    private String url;
    private String savePath;

    public static final ApkVersionHelper getInstance(Context context) {
        return new ApkVersionHelper(context);
    }

    private ApkVersionHelper(Context context) {
        this.context = context;
    }

    public ApkVersionHelper installTypeNotification() {
        installType = InstallType.Notification;
        return this;
    }

    public ApkVersionHelper installTypeDialog() {
        installType = InstallType.Dialog;
        return this;
    }

    public ApkVersionHelper mustInstall() {
        this.mustInstall = true;
        return this;
    }

    public ApkVersionHelper versionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public ApkVersionHelper versionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public ApkVersionHelper url(String url) {
        this.url = url;
        return this;
    }

    public ApkVersionHelper savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public final void checkUpdate() {
        try {
            if (context == null)
                throw new Exception("ApkVersionHelper context cannot be null,use context(Context context) to set it");
            if (TextUtils.isEmpty(url))
                throw new Exception("ApkVersionHelper download apk urlStreaming cannot be null,use url(String url) to set it");

            boolean vcb = versionCode == null;
            boolean vnb = TextUtils.isEmpty(versionName);
            if (vcb && vnb)
                throw new Exception("ApkVersionHelper need versionCode or versionName to check whether need to update,use versionCode(int versionCode) or versionName(String versionName) to set it");

            boolean update = false;

            if (!vcb) update = checkVersionCode();
            if (!vnb) update = checkVersionName();

            if (!update) return;

            if (mustInstall) installTypeDialog();

            showNoticeDialog();  //  更新
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final boolean checkVersionCode() throws Exception {
        return versionCode > AndroidUtils.getVersionCode(context);
    }

    private final boolean checkVersionName() throws Exception {
        return !StringUtils.isEqual(versionName, AndroidUtils.getVersionName(context));
    }

    private final void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("软件更新");

        String message;
        if (mustInstall)
            message = "发现新版本,请立即更新!";
        else
            message = "发现新版本,是否更新?";

        builder.setMessage(message);
        // 更新
        builder.setPositiveButton("马上更新", (dialog, which) -> {
                    switch (installType) {
                        case Dialog: {
                            installDialog();
                            progressDialog.show();
                            RxBus.register0(ApkVersionHelper.this, VUProgressMsg.class, vuProgressMsg -> {
                                progressBar.setProgress(vuProgressMsg.progress);
                                if (vuProgressMsg.progress == 100) {
                                    progressDialog.dismiss();
                                    RxBus.removeDisposable0(ApkVersionHelper.this, VUProgressMsg.class);
                                }
                            });
                            UpVersionService.start(context, url, savePath, false);
                        }
                        break;
                        case Notification: {
                            UpVersionService.start(context, url, savePath, true);
                        }
                        break;
                    }
                }
        );
        if (!mustInstall) {
            builder.setNegativeButton("稍后更新", null);
        }
        builder.setOnKeyListener((DialogInterface dialog, int keyCode, KeyEvent event) -> true);
        builder.setCancelable(false);
        builder.show();
    }

    private Dialog progressDialog;
    private ProgressBar progressBar;

    private final void installDialog() {

        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress_layout, null);

        TextView title = view.findViewById(R.id.tv_dialog_progress_title);
        progressBar = view.findViewById(R.id.pb_dialog_progress);

        progressDialog.setContentView(view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Window dialogWindow = progressDialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = displayMetrics.widthPixels / 10 * 9;
        // p.width = (int) (d.getWidth() * 0.65);
        dialogWindow.setAttributes(p);
//		view.getLayoutParams().width = width / 4 * 3;
    }

    public enum InstallType {
        Notification, Dialog
    }
}
