package c.g.a.x.lib_rxbus.rxbus;

/*
  Created by Administrator on 2018/2/9.
  支持背压，当数据量多时，能够防止报错
 */


import java.util.HashMap;
import java.util.Map;

import c.g.a.x.lib_base.BaseActivity;
import c.g.a.x.lib_base.BaseFragment;
import c.g.a.x.lib_rxbus.base.BaseMsg;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public final class RxBus {

    public static RxBus getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final RxBus INSTANCE = new RxBus();
    }

    //
    private final FlowableProcessor<Object> mBus;
    private final Map<String, CompositeDisposable> map = new HashMap<>();

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    //
    public static <T extends BaseMsg> void post0(@NonNull T obj) {
        getInstance().post(obj);
    }

    public <T extends BaseMsg> void post(@NonNull T obj) {
        mBus.onNext(obj);
    }

    public static <T extends BaseMsg> void register0(BaseActivity key, Class<T> clz, Consumer<T> consumer) {
        register0(key.toString() + clz.getName(), clz, consumer);
    }

    public static <T extends BaseMsg> void register0(BaseFragment key, Class<T> clz, Consumer<T> consumer) {
        register0(key.toString() + clz.getName(), clz, consumer);
    }


    public static <T extends BaseMsg> void register0(Object key, Class<T> clz, Consumer<T> consumer) {
        register0(key.toString() + clz.getName(), clz, consumer);
    }

    public static <T extends BaseMsg> void register0(String key, Class<T> clz, Consumer<T> consumer) {

        Disposable disposable = register0(clz, consumer);

        addDisposable0(key, disposable);
    }

    public static <T extends BaseMsg> Disposable register0(Class<T> clz, Consumer<T> consumer) {
        return register0(clz).subscribe(consumer);
    }

    public static <T extends BaseMsg> Flowable<T> register0(Class<T> clz) {
        return getInstance().register(clz);
    }

    public <T extends BaseMsg> Flowable<T> register(Class<T> tClass) {
        return register().ofType(tClass);
    }

    public Flowable<Object> register() {
        mBus.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return mBus;
    }


    public static <T> void removeDisposable0(BaseFragment key, Class<T> clz) {
        removeDisposable0(key.toString() + clz.getName());
    }

    public static <T> void removeDisposable0(BaseActivity key, Class<T> clz) {
        removeDisposable0(key.toString() + clz.getName());
    }

    public static <T> void removeDisposable0(Object key, Class<T> clz) {
        removeDisposable0(key.toString() + clz.getName());
    }


    public static void removeDisposable0(String key) {
        getInstance().removeDisposable(key);
    }

    public void removeDisposable(String key) {
        CompositeDisposable cd = getInstance().map.get(key);
        if (cd == null) return;
        cd.clear();
    }


    //
    public static void addDisposable0(String key, Disposable value) {
        getInstance().addDisposable(key, value);
    }

    public void addDisposable(String key, Disposable value) {
        CompositeDisposable cd = getInstance().map.get(key);
        if (cd == null) {
            cd = new CompositeDisposable();
            getInstance().map.put(key, cd);
        }
        cd.add(value);
    }

    //
    public boolean hasObservers() {
        return mBus.hasSubscribers();
    }

    public void unregisterAll() {
        //会将所有由mBus生成的Observable都置completed状态,后续的所有消息都收不到了
        mBus.onComplete();
    }
}