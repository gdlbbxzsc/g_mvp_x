package c.g.a.x.lib_http.http;


import android.content.Context;

import c.g.a.x.lib_http.rxjava2.filterobserver.BaseObserver;
import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.views.dialog.WaitDialogMnger;
import c.g.a.x.lib_support.views.toast.SysToast;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/12/4.
 */

public class BaseAction<S> {

    protected final Context context;

    protected Observable<S> observable;

    protected final HttpService service = HttpMgr.createService();

    protected BaseAction(Context context) {
        this.context = context;
    }

    public final Observable<S> toObservable() {
        return observable;
    }

    public <O extends BaseObserver> void subscribe(O o) {
        checkNetWork();
        observable.subscribe(o);
    }

    //默认调用，如无意外无需调用
    public final BaseAction<S> commonSchedulers() {
        observable = observable.compose(new CommonSchedulers<>());
        return this;
    }

    //若存在其他doOnSubscribe,且有可能调用disposable，那么尽量让progress按检索顺序(由下至上顺序)最后执行
    public final BaseAction<S> progress() {
        observable = observable.compose(new ProgressSchedulers<>());
        return this;
    }

    //默认调用，如无意外无需调用
    public final BaseAction<S> checkNetWork() {
        observable = observable.compose(new NetWorkSchedulers<>());
        return this;
    }
 
    protected final <T extends ObservableTransformer<S, S>> BaseAction<S> compose(T t) {
        observable = observable.compose(t);
        return this;
    }

    public final class CommonSchedulers<S> implements ObservableTransformer<S, S> {
        @Override
        public ObservableSource<S> apply(Observable<S> upstream) {
            return upstream
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io());
        }
    }

    public final class ProgressSchedulers<S> implements ObservableTransformer<S, S> {
        @Override
        public ObservableSource<S> apply(Observable<S> upstream) {
            return upstream
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> WaitDialogMnger.getInstance().cancel(context))
                    .doOnSubscribe(disposable -> WaitDialogMnger.getInstance().show(context))//
                    .subscribeOn(AndroidSchedulers.mainThread());
        }
    }

    public final class NetWorkSchedulers<S> implements ObservableTransformer<S, S> {
        @Override
        public ObservableSource<S> apply(Observable<S> upstream) {
            return upstream
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        if (!AndroidUtils.hasNetWork(context)) {
                            SysToast.showToastShort(context, "无网络连接");
                            disposable.dispose();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread());
        }
    }

}
