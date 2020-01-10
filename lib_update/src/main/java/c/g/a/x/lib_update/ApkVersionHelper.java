package c.g.a.x.lib_update;

import android.app.Dialog;
import android.content.Context;
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
import c.g.a.x.lib_sp.AccountSpHelper;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.utils.StringUtils;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.lib_support.views.toast.SysToast;


/**
 * Created by Administrator on 2018/7/24.
 */

public final class ApkVersionHelper {

    private final Context context;

    private InstallType installType = InstallType.Dialog;

    private boolean mustInstall;

    private List<String> noticeList = new ArrayList<>(8);

    private int versionCode;
    private String versionName;

    private String url;
    private String savePath;

    private UpdateDialog updateDialog;

    public static ApkVersionHelper getInstance(Context context) {
        return new ApkVersionHelper(context);
    }

    private ApkVersionHelper(Context context) {
        this.context = context;
    }

    public final ApkVersionHelper installTypeNotification() {
        installType = InstallType.Notification;
        return this;
    }

    public final ApkVersionHelper installTypeDialog() {
        installType = InstallType.Dialog;
        return this;
    }

    public final ApkVersionHelper installType(InstallType type) {
        installType = type;
        return this;
    }

    public final ApkVersionHelper mustInstall(boolean mustInstall) {
        this.mustInstall = mustInstall;
        return this;
    }

    public final ApkVersionHelper versionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public final ApkVersionHelper versionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public final ApkVersionHelper url(String url) {
        this.url = url;
        return this;
    }

    public final ApkVersionHelper savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public final ApkVersionHelper addNotice(List<String> list) {
        noticeList.addAll(list);
        return this;
    }

    public final ApkVersionHelper addNotice(String noticeLine) {
        noticeList.add(noticeLine);
        return this;
    }

    public final ApkVersionHelper clearNotice() {
        noticeList.clear();
        return this;
    }

    public final void updateDialog() {
        update();
        if (updateDialog != null) updateDialog.show();
    }

    public final UpdateDialog update() {
        check();
        return updateDialog;
    }

    private void check() {
        try {
            if (context == null)
                throw new Exception("ApkVersionHelper context cannot be null,use context(Context context) to set it");
            if (TextUtils.isEmpty(url))
                throw new Exception("ApkVersionHelper download apk urlStreaming cannot be null,use url(String url) to set it");

            boolean vcb = versionCode == 0;
            boolean vnb = TextUtils.isEmpty(versionName);
            if (vcb && vnb)
                throw new Exception("ApkVersionHelper need versionCode or versionName to check whether need to update,use versionCode(int versionCode) or versionName(String versionName) to set it");

            boolean update = false;

            if (!vnb) update = checkVersionName();
            if (!vcb) update = checkVersionCode();

            if (!update) return;

            if (mustInstall) installTypeDialog();

            //  更新
            if (noticeList.isEmpty()) noticeList.add(mustInstall ? "发现新版本,请立即更新!" : "发现新版本,是否更新?");

            updateDialog = new UpdateDialog(context);

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


    public enum InstallType {
        Notification, Dialog
    }


    public final class UpdateDialog extends Dialog {
        final View include_update_notice;
        final TextView tv_dialog_content;
        final TextView btn_dialog_no;
        final TextView btn_dialog_yes;

        final View include_update_progress;
        final ProgressBar progressBar;

        UpdateDialog(Context context) {
            super(context, R.style.DialogTheme);
            Objects.requireNonNull(getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setCancelable(false);

            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update, null);

            include_update_progress = rootView.findViewById(R.id.include_update_progress);
            tv_dialog_content = rootView.findViewById(R.id.tv_dialog_content);

            btn_dialog_yes = rootView.findViewById(R.id.btn_dialog_yes);
            btn_dialog_no = rootView.findViewById(R.id.btn_dialog_no);

            include_update_notice = rootView.findViewById(R.id.include_update_notice);
            progressBar = rootView.findViewById(R.id.pb_dialog_progress);

            step1();

            setContentView(rootView);
        }

        @Override
        public final void show() {
            super.show();
            Window dialogWindow = this.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
        }

        private void step1() {
            tv_dialog_content.setText("");
            for (String str : noticeList) {
                tv_dialog_content.append(str);
                tv_dialog_content.append("\n");
            }

            btn_dialog_no.setOnClickListener(new OnSPClickListener() {
                @Override
                public void onClickSucc(View v) {
                    dismiss();
                }
            });

            btn_dialog_yes.setOnClickListener(new OnSPClickListener() {
                @Override
                public void onClickSucc(View v) {
                    step2();
                }
            });
            btn_dialog_no.setVisibility(mustInstall ? View.GONE : View.VISIBLE);

            include_update_notice.setVisibility(View.VISIBLE);
            include_update_progress.setVisibility(View.GONE);
        }

        private void step2() {
            switch (installType) {
                case Dialog: {
                    updateDialog();
                }
                break;
                case Notification: {
                    updateNotification();
                }
                break;
            }
        }

        private void updateDialog() {
            include_update_notice.setVisibility(View.GONE);
            include_update_progress.setVisibility(View.VISIBLE);

            RxBus.register0(ApkVersionHelper.this, VUProgressMsg.class, vuProgressMsg -> {
                switch (vuProgressMsg.state) {
                    case 0:
                        progressBar.setProgress(vuProgressMsg.progress);
                        break;
                    case 1:
                        SysToast.showToastShort(context, "下载成功");
                        new AccountSpHelper().putFirstUse(true);
                        dismiss();
                        RxBus.removeDisposable0(ApkVersionHelper.this, VUProgressMsg.class);
                        break;
                    default:
                        SysToast.showToastShort(context, "下载失败");

                        dismiss();
                        RxBus.removeDisposable0(ApkVersionHelper.this, VUProgressMsg.class);
                        break;
                }
            });
            UpVersionService.start(context, url, savePath, false);
        }

        private void updateNotification() {
            UpVersionService.start(context, url, savePath, true);
            dismiss();
        }
    }
}
