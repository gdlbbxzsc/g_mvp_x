package c.g.a.x.global_application.rxjava2.filterobserver;

import android.content.Context;

import c.g.a.x.lib_http.base.BaseResponse;


public abstract class LogoutFilteErrorObserver<T extends BaseResponse> extends LogoutObserver<T> {

    public LogoutFilteErrorObserver(Context context) {
        super(context, true);
    }

}
