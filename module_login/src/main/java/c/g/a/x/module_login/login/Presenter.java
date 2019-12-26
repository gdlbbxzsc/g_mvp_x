package c.g.a.x.module_login.login;


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


    @Override
    public void getImageCode(String phone) {

    }

    @Override
    public void login() {

    }

    @Override
    public void register() {

    }
}
