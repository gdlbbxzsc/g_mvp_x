package c.g.a.x.lib_mvp.fragment;


import androidx.viewbinding.ViewBinding;

import c.g.a.x.lib_support.base.BaseFragment;
import c.g.a.x.lib_mvp.base.IBasePresenter;


public abstract class MvpFragment<D extends ViewBinding, P extends IBasePresenter> extends BaseFragment<D> {

    public P presenter;

    public void onCreateViewFirst() {
        super.onCreateViewFirst();
        bindPresenter();
        presenter.subscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解绑 presenter
        if (presenter != null) {
            presenter.unsubscribe();
        }
    }


    public MvpFragment getFragment() {
        return (MvpFragment) fragment;
    }

    public void bindPresenter() {

        P presenter = createPresenter();

        if (presenter == null) return;

        this.presenter = presenter;

    }

    protected abstract P createPresenter();


}
