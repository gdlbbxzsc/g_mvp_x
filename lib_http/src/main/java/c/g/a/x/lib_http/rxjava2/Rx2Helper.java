package c.g.a.x.lib_http.rxjava2;

import android.content.Context;


import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.views.dialog.WaitDialogMnger;
import c.g.a.x.lib_support.views.toast.SysToast;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class Rx2Helper {

    public static <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable<T> addProgress(Observable<T> observable, Context context) {
        if (context == null) return observable;
        return observable//
                .doOnSubscribe(disposable -> WaitDialogMnger.getInstance().show(context))//
                .doFinally(() -> WaitDialogMnger.getInstance().cancel(context));
    }

    public static <T> Observable<T> addNetWorkCheck(Observable<T> observable, Context context) {
        if (context == null) return observable;
        return observable.doOnSubscribe(disposable -> {
            if (!AndroidUtils.hasNetWork(context)) {
                SysToast.showToastShort(context, "无网络连接");
                disposable.dispose();
            }
        });
    }


}
