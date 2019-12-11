package c.g.a.x.lib_update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.utils.StringUtils;
import c.g.a.x.lib_support.views.dialog.MyDialog;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.lib_support.views.toast.SysToast;


/**
 * Created by Administrator on 2018/7/24.
 */

public final class ApkVersionHelper {

    private final Context context;

    private InstallType installType = InstallType.Dialog;
    private boolean mustInstall;

    private Integer versionCode;
    private String versionName;
    private String url;
    private String savePath;

    public static ApkVersionHelper getInstance(Context context) {
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

    private boolean checkVersionCode() throws Exception {
        return versionCode > AndroidUtils.getVersionCode(context);
    }

    private boolean checkVersionName() throws Exception {
        return !StringUtils.isEqual(versionName, AndroidUtils.getVersionName(context));
    }

    private void showNoticeDialog() {

        List<String> upgradeInformation = new ArrayList<>(3);
        if (mustInstall)
            upgradeInformation.add("发现新版本,请立即更新!");
        else
            upgradeInformation.add("发现新版本,是否更新?");

        new NoticeDialog(context, upgradeInformation, mustInstall, onClickListener).show();
    }

    private final MyDialog.OnClickListener onClickListener = new MyDialog.OnClickListener() {
        @Override
        public boolean onClick(DialogInterface dialog, int which) {

            switch (installType) {
                case Dialog: {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.show();
                    RxBus.register0(ApkVersionHelper.this, VUProgressMsg.class, vuProgressMsg -> {
                        switch (vuProgressMsg.state) {
                            case 0:
                                progressDialog.progressBar.setProgress(vuProgressMsg.progress);
                                break;
                            case 1:
                                SysToast.showToastShort(context, "下载成功");

                                progressDialog.dismiss();
                                RxBus.removeDisposable0(ApkVersionHelper.this, VUProgressMsg.class);
                                break;
                            default:
                                SysToast.showToastShort(context, "下载失败");

                                progressDialog.dismiss();
                                RxBus.removeDisposable0(ApkVersionHelper.this, VUProgressMsg.class);
                                break;
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
            return true;
        }
    };

    public enum InstallType {
        Notification, Dialog
    }


    private final class NoticeDialog extends Dialog {

        public final TextView textView2;
        public final TextView btn_dialog_no;
        public final TextView btn_dialog_yes;

        public NoticeDialog(Context context, List<String> list, boolean mustInstall, MyDialog.OnClickListener listener) {
            super(context, R.style.DialogTheme);
            Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setCancelable(false);

            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_notice, null);

            this.textView2 = rootView.findViewById(R.id.textView2);
            textView2.setText("");
            if (list != null && !list.isEmpty()) {
                for (String str : list) {
                    textView2.append(str);
                    textView2.append("\n");
                }
            }

            this.btn_dialog_no = rootView.findViewById(R.id.btn_dialog_no);
            btn_dialog_no.setOnClickListener(new OnSPClickListener() {
                @Override
                public void onClickSucc(View v) {
//                    listener.onClick(NoticeDialog.this, 0);
                    dismiss();
                }
            });

            this.btn_dialog_yes = rootView.findViewById(R.id.btn_dialog_yes);
            btn_dialog_yes.setOnClickListener(new OnSPClickListener() {
                @Override
                public void onClickSucc(View v) {
                    boolean b = listener.onClick(NoticeDialog.this, 1);
                    if (b) dismiss();
                }
            });
            btn_dialog_no.setVisibility(mustInstall ? View.GONE : View.VISIBLE);
            setContentView(rootView);
        }

        @Override
        public void show() {
            super.show();
            Window dialogWindow = this.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        }
    }

    private ProgressDialog progressDialog;

    private final class ProgressDialog extends Dialog {

        public final ProgressBar progressBar;

        public ProgressDialog(Context context) {
            super(context, R.style.DialogTheme);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setCancelable(false);

            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update_progress, null);

            progressBar = rootView.findViewById(R.id.pb_dialog_progress);

            setContentView(rootView);
        }


        @Override
        public void show() {
            super.show();
            Window dialogWindow = this.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        }
    }
}
