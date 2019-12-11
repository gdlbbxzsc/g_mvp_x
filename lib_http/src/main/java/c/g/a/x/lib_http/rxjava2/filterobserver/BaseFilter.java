package c.g.a.x.lib_http.rxjava2.filterobserver;

import android.content.Context;


import java.util.HashMap;
import java.util.Map;

import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.rxjava2.errorlistener.OnErrorToastShortListener;

public abstract class BaseFilter {

    private final static String THREAD_MAIN = "main";

    private Context mContext;

    private final Map<Object, Error> filterMap = new HashMap<>();

    public BaseFilter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public boolean filterThrowableError(Throwable t) {
        Error error = filterMap.get(t.getClass());
        if (error == null) return true;
        onErrorDo(error);
        return false;
    }

    protected <T extends BaseResponse> boolean filterDataError(T t) {
        Error error = filterMap.get(t.getCode());
        if (error == null) return true;
        onErrorDo(error);
        return false;
    }

    public final void onErrorDo(Error error) {
        try {
            if (!isMainThread()) return;
            OnErrorListener errorListener = (OnErrorListener) error.cls.newInstance();
            errorListener.onDo(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMainThread() {
        if (mContext == null) return false;
        return Thread.currentThread().getName().equals(THREAD_MAIN);
    }


    public void putThrowableError(Class cls, String msg) {
        putError(cls, msg, OnErrorToastShortListener.class);
    }

    public void putThrowableError(Class cls, String msg, Class listener) {
        putError(cls, msg, listener);
    }

    public void putDataError(int code, String msg) {
        putError(code, msg, OnErrorToastShortListener.class);
    }

    public void putDataError(int code, String msg, Class listener) {
        putError(code, msg, listener);
    }

    public final void putError(Object obj, String msg, Class listener) {
        filterMap.put(obj, new Error(mContext, msg, listener));
    }

    public static class Error {

        public final Context context;

        public final Class cls;

        public final String msg;

        public Error(Context context, String msg, Class cls) {

            this.context = context;

            this.cls = cls;

            this.msg = msg;
        }
    }

    public interface OnErrorListener {
        void onDo(Error error);
    }
}
