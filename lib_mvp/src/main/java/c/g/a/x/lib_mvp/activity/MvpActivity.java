package c.g.a.x.lib_mvp.activity;


import androidx.databinding.ViewDataBinding;

import c.g.a.x.lib_base.BaseActivity;
import c.g.a.x.lib_mvp.base.IBasePresenter;


public abstract class MvpActivity< D extends ViewDataBinding, P extends IBasePresenter> extends BaseActivity<D> {

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
