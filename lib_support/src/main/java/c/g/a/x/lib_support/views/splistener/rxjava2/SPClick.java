package c.g.a.x.lib_support.views.splistener.rxjava2;//package com.gdl.android.view_lib.singlepointlistener.rxjava2;
//
//import android.view.View;
//
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.functions.Consumer;
//
///**
// * Created by Administrator on 2018/7/18.
//// * 需要lambda表达式支持和 rx库支持 不想导入 就注释掉了 想用的话自行解决
//        new SPClick<>(tv).subscribe(view -> {
//            Logger.e("===");
//
//
//        });
// */
//
//public class SPClick<T extends View> implements ObservableOnSubscribe {
//
//    private T view;
//
//    public SPClick(T view) {
//        this.view = view;
//    }
//
//
//    @Override
//    public void subscribe(ObservableEmitter e) throws Exception {
//        view.setOnClickListener(v -> e.onNext(view));
//    }
//
//
//    public void subscribe(Consumer<T> consumer) {
//        Observable.create(this).throttleFirst(100, TimeUnit.MILLISECONDS).subscribe(consumer);
//    }
//}
