package c.g.a.x.module_login.register;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_sp.AccountSpHelper;
import c.g.a.x.module_login.R;
import c.g.a.x.module_login.databinding.ActivityRegisterBinding;


@Route(path = Constant.REGISTER_ACTIVITY)
public class RegisterActivity extends MvpActivity<ActivityRegisterBinding, Presenter> implements Contract.View {

    @Override
    protected int layoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void getDataFromPreActivity() throws Exception {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter<>(this);
    }

    @Override
    protected void initView() {
        binder.tv.setText(new AccountSpHelper().getAccount());
    }

    @Override
    protected void initData() {

    }
}
