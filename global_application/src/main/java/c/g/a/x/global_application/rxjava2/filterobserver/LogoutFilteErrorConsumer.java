package c.g.a.x.global_application.rxjava2.filterobserver;

import android.content.Context;

import c.g.a.x.lib_http.base.BaseResponse;


public abstract class LogoutFilteErrorConsumer<T extends BaseResponse> extends LogoutConsumer<T> {

    public LogoutFilteErrorConsumer(Context context) {
        super(context, true);
    }
}
