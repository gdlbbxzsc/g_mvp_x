package c.g.a.x.lib_mvp.activity;


import androidx.viewbinding.ViewBinding;

import c.g.a.x.lib_support.base.BaseActivity;
import c.g.a.x.lib_mvp.base.IBasePresenter;


public abstract class MvpActivity< D extends ViewBinding, P extends IBasePresenter> extends BaseActivity<D> {

    public P presenter;

    @Override
    public void afterInitView() {
        super.afterInitView();
        bindPresenter();
        presenter.subscribe();
    }

    public void bindPresenter() {

        P presenter = createPresenter();

        if (presenter == null) return;

        this.presenter = presenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
        }

    }

    public MvpActivity getActivity() {
        return (MvpActivity) activity;
    }


    protected abstract P createPresenter();
}
