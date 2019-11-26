package c.g.a.x.lib_update;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import c.g.a.x.lib_http.http.HttpMgr;
import c.g.a.x.lib_rxbus.rxbus.RxBus;
import c.g.a.x.lib_support.android.utils.FileHelper;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.utils.FileUtils;
import c.g.a.x.lib_support.utils.IOUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/11.
 * <p>
 * UpVersionService.start(mContext,mSavePath,mDownloadUrl);调用升级程序
 */

public final class UpVersionService extends Service {

    private final int NOTIFICATION_ID = 0x12321 + (int) Long.parseLong(UUID.randomUUID().toString().replace("-", ""));
    private final int NOTIFICATION_REQUESTCODE = 0x12221 + (int) Long.parseLong(UUID.randomUUID().toString().replace("-", ""));
    private final int PROGRESS_MAX = 100;

    final String CHANNEL_ID = "channel_id_" + getPackageName().toLowerCase();
    final String CHANNEL_NAME = "channel_name_xag" + getPackageName().toLowerCase();

    //true it will be auto start install activity and cancel the notification.otherwise nothing.
    private boolean AUTO_GO_INSTALL = true;

    private Context context;

    private NotificationManager mNotificationManager;

    private boolean notification;// 以何种方式通知用户进度 true 通知栏 false 进度条模式
    private String mDownloadUrl;//下载的Url
    private String localPath;
    private File saveFile;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Logger.e("======UpVersionService start down=========");
            context = this;
            mDownloadUrl = intent.getStringExtra("download_url");
            localPath = intent.getStringExtra("local_path");
            notification = intent.getBooleanExtra("notification", false);
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            createChannel();
            downFile();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @SuppressLint("CheckResult")
    private void downFile() {
        notify2User(0);

        HttpMgr.createService().urlStreaming(mDownloadUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(body -> {
                    Logger.e("down file");
                    InputStream is = body.byteStream();

                    if (TextUtils.isEmpty(localPath))
                        saveFile = FileHelper.getInstance().toAppPath().fileSubPath(Constant.PATH_DOWNLOAD).fileName(Constant.FILE_DOWN_APK).creat();
                    else
                        saveFile = FileUtils.createFile(localPath);

                    long fileSize = body.contentLength();

                    IOUtils.writeData2File(is, saveFile, pro -> {
                        Logger.e(pro);
                        int num = (int) (1D * pro / fileSize * PROGRESS_MAX);
                        notify2User(num);
                    });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(body -> finish(true), throwable -> finish(false));
    }

    void finish(boolean succ) {
        if (notification) {
            mNotificationManager.cancel(NOTIFICATION_ID);
        } else {
            //   dialog进度条模式强制安装
            AUTO_GO_INSTALL = true;
        }

        if (succ && AUTO_GO_INSTALL) installApk();

        stopSelf();
    }

    //    通知栏通知
    private void notify2User(int progress) {

        Logger.e("100:" + progress);
        if (notification) {
            PendingIntent pendingIntent = null;
            if (progress >= PROGRESS_MAX) {
                Intent intent = getInstallIntent();
                pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUESTCODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            mNotificationManager.notify(NOTIFICATION_ID, buildNotificationCompat(progress, pendingIntent).build());
        } else {
            VUProgressMsg msg = new VUProgressMsg();
            msg.progress = progress;
            RxBus.post0(msg);
        }
    }

    private final void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private final NotificationCompat.Builder buildNotificationCompat(int progress, PendingIntent pendingIntent) {
        return new NotificationCompat
                .Builder(context, context.getPackageName())
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(false)
                .setWhen(System.currentTimeMillis())
                .setProgress(PROGRESS_MAX, progress, false)
                .setContentIntent(pendingIntent);
    }

    private void installApk() {
        Intent intent = getInstallIntent();
        startActivity(intent);
    }

    private Intent getInstallIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), ".VersionUpFileProvider", saveFile);//在AndroidManifest中的android:authorities值
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
        }
        return intent;
    }

    public static final void start(Context context, String downloadUrl, String local_path, boolean notification) {
        Intent intent = new Intent(context, UpVersionService.class);
        intent.putExtra("download_url", downloadUrl);
        intent.putExtra("local_path", local_path);
        intent.putExtra("notification", notification);
        context.startService(intent);
    }
}