package c.g.a.x.module_chat.detail;


import c.g.a.x.lib_mvp.base.BasePresenter;

public class Presenter<T extends Contract.View> extends BasePresenter<T> implements Contract.Presenter {


    public Presenter(T baseView) {
        super(baseView);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }

}