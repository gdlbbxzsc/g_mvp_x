package c.g.a.x.lib_http.rxjava2.filterobserver;

import android.content.Context;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import c.g.a.x.lib_http.rxjava2.errorlistener.OnErrorToastShortListener;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public class BaseErrorConsumer<T extends Throwable> extends BaseFilter implements Consumer<T> {

    public BaseErrorConsumer(Context context) {
        super(context);

        putThrowableError(HttpException.class, "网络错误");
        putThrowableError(IOException.class, "没有网络，请检查网络连接");
        putThrowableError(SocketTimeoutException.class, "服务器响应超时");
        putThrowableError(ConnectException.class, "网络连接异常，请检查网络");
        putThrowableError(UnknownHostException.class, "无法解析主机，请检查网络连接", OnErrorToastShortListener.class);
        putThrowableError(OnErrorNotImplementedException.class, "unknow");

    }

    @Override
    public final void accept(T throwable) throws Exception {
        try {
            throwable.printStackTrace();
            //true means all of the error we use putErrorFilter in list dosnt happened,otherwise throwable is one of it.
            boolean b = filterThrowableError(throwable);
            if (!b) return;

            onDo(throwable);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            finalDo();
        }
    }

    public void onDo(T throwable) throws Exception {
    }

    public void finalDo() throws Exception {
    }

}
