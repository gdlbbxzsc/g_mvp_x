package c.g.a.x.lib_http.http;


import android.content.Context;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class UrlAction<R extends ResponseBody> extends BaseAction<R> {

    public static <R extends ResponseBody> UrlAction<R> context(Context context) {
        return new UrlAction<>(context);
    }

    protected UrlAction(Context context) {
        super(context);
    }

    public UrlAction urlStreaming(String url) {
        observable = (Observable<R>) service.urlStreaming(url);
        commonSchedulers();
        return this;
    }

    public UrlAction url(String url) {
        observable = (Observable<R>) service.url(url);
        commonSchedulers();
        return this;
    }

}
