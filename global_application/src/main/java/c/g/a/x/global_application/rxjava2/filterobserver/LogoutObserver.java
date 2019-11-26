package c.g.a.x.global_application.rxjava2.filterobserver;

import android.content.Context;

import c.g.a.x.global_application.rxjava2.errorlistener.OnErrorLogoutListener;
import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.rxjava2.filterobserver.BaseObserver;


public abstract class LogoutObserver<T extends BaseResponse> extends BaseObserver<T> {

    public LogoutObserver(Context context) {
        this(context, false);
    }

    public LogoutObserver(Context context, boolean filterAllError) {
        super(context, filterAllError);
        putDataError(300, "请登录", OnErrorLogoutListener.class);
    }


}
