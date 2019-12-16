package c.g.a.x.module_main.main;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;


@Route(path = Constant.MAIN_ACTIVITY)
public class MainActivity extends MvpActivity<ActivityMainBinding, Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }


    @Override
    protected void initView() {
        NotificationHelper.checkEnabledDialog(context);

//        ImageLoader.loadHead(context, binder.iv1, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");
//        ImageLoader.load(context, binder.iv2, "http://f.hiphotos.baidu.com/zhidao/pic/item/3c6d55fbb2fb4316984c0f4122a4462309f7d3be.jpg");

//        PlaylistBox box = new PlaylistBox();
//        box.deleteAll();
//        Playlist vo = new Playlist();
//        vo.name = "连佳凡";
//        box.insert(vo);
//        vo = box.select("gdl");
//        Logger.e(vo.id, "======== ", vo.name);


        Observable.just(0)
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "0", "1", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "0", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "0", "1", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "0", "1", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "0", "2", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "0", "2", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "0", "2", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "0", "2", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "0", "3", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "0", "3", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "0", "3", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "0", "3", "===+++>", Thread.currentThread().getName());
                    return 0;
                })

                .subscribeOn(Schedulers.io())

                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "1", "1", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "1", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "1", "1", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "1", "1", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "1", "2", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "1", "2", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "1", "2", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "1", "2", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "1", "3", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "1", "3", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "1", "3", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "1", "3", "===+++>", Thread.currentThread().getName());
                    return 0;
                })

                .subscribeOn(Schedulers.newThread())

                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "2", "1", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "2", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "2", "1", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "2", "1", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "2", "2", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "2", "2", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "2", "2", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "2", "2", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "2", "3", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "2", "3", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "2", "3", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "2", "3", "===+++>", Thread.currentThread().getName());
                    return 0;
                })

                .observeOn(Schedulers.newThread())

                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "3", "1", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "3", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "3", "1", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "3", "1", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "3", "2", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "3", "2", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "3", "2", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "3", "2", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "3", "3", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "3", "3", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "3", "3", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "3", "3", "===+++>", Thread.currentThread().getName());
                    return 0;
                })

                .observeOn(Schedulers.io())

                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "4", "1", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "4", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "4", "1", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "4", "1", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "4", "2", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "4", "2", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "4", "2", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "4", "2", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "4", "3", "===+++>", Thread.currentThread().getName()))
                .doFinally(() -> Logger.e("doFinally", "4", "3", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "4", "3", "===+++>", Thread.currentThread().getName()))
                .map(integer -> {
                    Logger.e("map", "4", "3", "===+++>", Thread.currentThread().getName());
                    return 0;
                })
                .subscribe(integer -> Logger.e("subscribe", "===+++>", Thread.currentThread().getName()));
    }


    @Override
    protected void initData() {
    }


    @AfterPermissionGranted(Presenter.REQUEST_CODE_4_DOSOMETHING)
    @Override
    public void doSomeThing() {
    }

}
