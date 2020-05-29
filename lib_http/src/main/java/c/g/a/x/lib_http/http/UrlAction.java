package c.g.a.x.lib_http.http;


import android.content.Context;

import java.io.File;

import c.g.a.x.lib_support.android.utils.AlbumNotifyHelper;
import c.g.a.x.lib_support.android.utils.FileHelper;
import c.g.a.x.lib_support.utils.FileUtils;
import c.g.a.x.lib_support.utils.IOUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class UrlAction<R extends ResponseBody> extends BaseAction<R> {

    private String url;

    public static <R extends ResponseBody> UrlAction<R> context(Context context) {
        return new UrlAction<>(context);
    }

    protected UrlAction(Context context) {
        super(context);
    }

    public UrlAction urlStreaming(String url) {
        this.url = url;
        observable = (Observable<R>) service.urlStreaming(url);
        commonSchedulers();
        return this;
    }

    public UrlAction url(String url) {
        this.url = url;
        observable = (Observable<R>) service.url(url);
        commonSchedulers();
        return this;
    }

    public UrlAction upload(String url, File file) {
        this.url = url;

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        observable = (Observable<R>) service.upload(url, body);

        commonSchedulers();
        return this;
    }


    public <O extends Observer> void subscribe(O o) {
        checkNetWork();
        observable.subscribe(o);
    }

    public final UrlAction<R> downPhoto() {
        observable = observable.compose(new DownPhotoTransformer<>());
        return this;
    }

    public final void downPhoto(Consumer<? super R> onNext, Consumer<? super Throwable> onError) {
        downPhoto();
        observable.subscribe(onNext, onError);
    }

    public final class DownPhotoTransformer<R extends ResponseBody> implements ObservableTransformer<R, R> {
        @Override
        public ObservableSource<R> apply(Observable<R> upstream) {
            return upstream
                    .observeOn(Schedulers.computation()) // 用于计算任务
                    .doOnNext(body -> {
                        File file = FileHelper.getInstance().toCameraPath().fileName(FileUtils.makeNameByMD5(url)).create();
                        IOUtils.writeData2File(body.byteStream(), file);
                        AlbumNotifyHelper.getInstance(context).insertPhoto(file.getAbsolutePath()).notifyByBroadcast();
                    })
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

}
