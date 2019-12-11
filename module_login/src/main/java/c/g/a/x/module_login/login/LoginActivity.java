package c.g.a.x.module_login.login;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.module_login.R;
import c.g.a.x.module_login.databinding.ActivityLoginBinding;


@Route(path = Constant.LOGIN_ACTIVITY)
public class LoginActivity extends MvpActivity<ActivityLoginBinding, Presenter> implements Contract.View {


    @Override
    protected int layoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }


    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

}
