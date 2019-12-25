package c.g.a.x.module_main.main.index;


import c.g.a.x.lib_mvp.base.IBasePresenter;
import c.g.a.x.lib_mvp.fragment.IBaseFragmentView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface Contract {

    interface View extends IBaseFragmentView {
    }

    interface Presenter extends IBasePresenter {
    }
}
