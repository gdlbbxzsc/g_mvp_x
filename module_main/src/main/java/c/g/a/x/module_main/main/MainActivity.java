package c.g.a.x.module_main.main;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.android.utils.NotificationHelper;
import c.g.a.x.module_main.R;
import c.g.a.x.module_main.databinding.ActivityMainBinding;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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



                .observeOn(Schedulers.newThread())
                .doFinally(() -> Logger.e("doFinally", "3", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "3", "1", "===+++>", Thread.currentThread().getName()))
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "3", "1", "===+++>", Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newThread())

                .observeOn(Schedulers.io())
                .doFinally(() -> Logger.e("doFinally", "2", "1", "===+++>", Thread.currentThread().getName()))
                .doOnNext(integer -> Logger.e("doOnNext", "2", "1", "===+++>", Thread.currentThread().getName()))
                .doOnSubscribe(disposable -> Logger.e("doOnSubscribe", "2", "1", "===+++>", Thread.currentThread().getName()))
                .subscribeOn(Schedulers.io())

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
