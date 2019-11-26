package c.g.a.x.lib_http.rxjava2.filterobserver;

import android.content.Context;

import c.g.a.x.lib_http.base.BaseResponse;


public abstract class FilteErrorConsumer<T extends BaseResponse> extends BaseConsumer<T> {


    public FilteErrorConsumer(Context context) {
        super(context, true);
    }
}
