package c.g.a.x.module_login.login;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.global_application.sp.AccountSpHelper;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.module_login.R;
import c.g.a.x.module_login.databinding.ActivityLoginBinding;


@Route(path = Constant.LOGIN_ACTIVITY)
public class LoginActivity extends MvpActivity<ActivityLoginBinding, Presenter> implements Contract.View {

    private final AccountSpHelper accountSpHelper = new AccountSpHelper();

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
        //记住密码
        boolean remember = accountSpHelper.isRemember();
        binder.includeLoginRemember.cbRemember.setChecked(remember);
        if (remember) {
            binder.includeLoginAccount.edtAccount.setText(accountSpHelper.getAccount());
            binder.includeLoginPassword.edtPassword.setText(accountSpHelper.getPassword());
        }

        //        同意协议
        binder.includeLoginAgree.cbAgreement.setChecked(accountSpHelper.isAgree());
        binder.includeLoginAgree.tvAgreement.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                binder.includeLoginAgree.cbAgreement.setChecked(binder.includeLoginAgree.cbAgreement.isChecked());
            }
        });

        //        自动登录
        boolean auto = accountSpHelper.isAutoLogin();
        binder.includeLoginAuto.cbAuto.setChecked(auto);
        if (auto) {
            presenter.login();
        }

        //        登录
        binder.includeLoginBtnlogin.btnLogin.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
//                presenter.login();

            }
        });
        //注册
        binder.includeLoginBtnregister.btnRegister.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                presenter.register();
            }
        });
        binder.includeLoginImageCode.ivCode.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                String acc = binder.includeLoginAccount.edtAccount.getText().toString().trim();
                presenter.getImageCode(acc);
            }
        });

    }


}
