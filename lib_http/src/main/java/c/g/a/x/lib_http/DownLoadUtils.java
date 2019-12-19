package c.g.a.x.lib_http;


import android.content.Context;

import java.io.File;

import c.g.a.x.lib_support.android.utils.AlbumNotifyHelper;
import c.g.a.x.lib_support.android.utils.FileHelper;
import c.g.a.x.lib_support.utils.FileUtils;
import c.g.a.x.lib_support.utils.IOUtils;
import c.g.a.x.lib_support.views.toast.SysToast;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class DownLoadUtils {
    public static void downloadImage(Context context, String url) {
        Observable<ResponseBody> observable = HttpAction.context(context).url(url).toObservable();

        observable
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(body -> {
                    File file = FileHelper.getInstance().toCameraPath().fileName(FileUtils.makeNameByMD5(url)).create();
                    IOUtils.writeData2File(body.byteStream(), file);
                    AlbumNotifyHelper.getInstance(context).insertPhoto(file.getAbsolutePath()).notifyByBroadcast();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        body -> SysToast.showToastShort(context, "下载成功"),
                        throwable -> {
                            throwable.printStackTrace();
                            SysToast.showToastShort(context, "下载失败");
                        });
    }
}
