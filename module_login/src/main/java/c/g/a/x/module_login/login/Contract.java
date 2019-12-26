package c.g.a.x.module_login.login;


import android.graphics.Bitmap;

import c.g.a.x.lib_mvp.activity.IBaseActivityView;
import c.g.a.x.lib_mvp.base.IBasePresenter;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface Contract {

    //看清楚哦 这里继承的是 IBaseActivityView ，还有一个BaseFragmentView，为什么就不用我多说了吧。
    interface View extends IBaseActivityView {
        
        void getImageCode(Bitmap bitmap);

        void sendSmsCodeSucc();

        void sendSmsCodeFail();

        void login();
    }

    interface Presenter extends IBasePresenter {

        void getImageCode(String phone);

        void sendSmsCode(String phone);

        void login(String phone);

    }
}
