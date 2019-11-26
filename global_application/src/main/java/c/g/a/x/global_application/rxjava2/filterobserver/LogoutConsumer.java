package c.g.a.x.global_application.rxjava2.filterobserver;

import android.content.Context;

import c.g.a.x.global_application.rxjava2.errorlistener.OnErrorLogoutListener;
import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.rxjava2.filterobserver.BaseConsumer;


public abstract class LogoutConsumer<T extends BaseResponse> extends BaseConsumer<T> {

    public LogoutConsumer(Context context) {
        this(context, false);
    }

    public LogoutConsumer(Context context, boolean filterAllError) {
        super(context, filterAllError);
        putDataError(300, "请登录", OnErrorLogoutListener.class);
    }
}
