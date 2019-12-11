package c.g.a.x.lib_http.rxjava2.filterobserver;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.rxjava2.errorlistener.OnErrorToastShortListener;
import c.g.a.x.lib_support.android.utils.Logger;
import io.reactivex.Observer;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import retrofit2.HttpException;


public abstract class BaseObserver<T extends BaseResponse> extends BaseFilter implements Observer<T> {

    protected final boolean filterAllError;

    public BaseObserver(Context context) {
        this(context, false);
    }

    public BaseObserver(Context context, boolean filterAllError) {
        super(context);

        this.filterAllError = filterAllError;

        putDataError(0, "数据错误", OnErrorToastShortListener.class);

        putThrowableError(HttpException.class, "网络错误");
        putThrowableError(IOException.class, "没有网络，请检查网络连接");
        putThrowableError(SocketTimeoutException.class, "服务器响应超时");
        putThrowableError(ConnectException.class, "网络连接异常，请检查网络");
        putThrowableError(UnknownHostException.class, "无法解析主机，请检查网络连接", OnErrorToastShortListener.class);
        putThrowableError(OnErrorNotImplementedException.class, "服务器错误");
    }

    @Override
    public final void onNext(T t) {
        Logger.e(this.toString() + "====>onNext");
        try {
            boolean b = filterDataError(t);
            if (!b) {
                finalDo();
                return;
            }
            if (filterAllError && t.getCode() != 200) {
                Toast.makeText(getContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                finalDo();
                return;
            }
            onNextDo(t);
            finalDo();
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public final void onError(Throwable e) {
        Logger.e(this.toString() + "====>onError");
        e.printStackTrace();
        try {
            boolean b = filterThrowableError(e);
            if (!b) return;
            onErrorDo(e);
        } catch (Exception t) {
            t.printStackTrace();
        } finally {
            finalDo();
        }
    }

    public abstract void onNextDo(T vo);

    public void onErrorDo(Throwable e) {
    }

    public void finalDo() {
    }
}
