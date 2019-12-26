package c.g.a.x.module_login.login;

import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import c.g.a.x.global_application.arouter.Constant;
import c.g.a.x.global_application.sp.AccountSpHelper;
import c.g.a.x.lib_mvp.activity.MvpActivity;
import c.g.a.x.lib_support.views.splistener.custom.OnSPClickListener;
import c.g.a.x.lib_support.views.toast.SysToast;
import c.g.a.x.module_login.CheckUtils;
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
//        账号
//        binder.includeLoginAccount.edtAccount.setKeyListener(new NumberKeyListener() {   });
//        binder.includeLoginAccount.edtAccount.setFilters(new InputFilter[]{new MyInputFilter("\"^1[0-9]\\\\d{0,9}$\"")});
        binder.includeLoginAccount.edtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                String checkRes = CheckUtils.checkAccount(str);
                if (checkRes != null) return;

                SysToast.showToastShort(context, "账号输入完成,请做下一步请求");
            }
        });
//        密码
//        binder.includeLoginPassword.edtPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                String str = s.toString().trim();
////                String checkRes = checkPassword(str);
////                if (checkRes != null) return;
////
////                SysToast.showToastShort(context, "密码输入完成,请做下一步请求");
//            }
//        });

//图形验证码
        binder.includeLoginImageCode.edtImageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                String checkRes = CheckUtils.checkImageCode(str);
                if (checkRes != null) return;
                SysToast.showToastShort(context, "图形验证码输入完成,请做下一步请求");
            }
        });
        binder.includeLoginImageCode.btnImageCode.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                doGetImageCode();
            }
        });
//短信验证码
        binder.includeLoginSmsCode.edtSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                String checkRes = CheckUtils.checkSmsCode(str);
                if (checkRes != null) return;
                SysToast.showToastShort(context, "短信验证码输入完成,请做下一步请求");
            }
        });
        binder.includeLoginSmsCode.btnSmsCode.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                doSendSmsCode();
            }
        });
        //        同意协议
        binder.includeLoginAgree.cbAgreement.setChecked(accountSpHelper.isAgree());
        binder.includeLoginAgree.tvAgreement.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                binder.includeLoginAgree.cbAgreement.setChecked(binder.includeLoginAgree.cbAgreement.isChecked());
            }
        });

        //记住密码
        boolean remember = accountSpHelper.isRemember();
        binder.includeLoginRemember.cbRemember.setChecked(remember);
        if (remember) {
            binder.includeLoginAccount.edtAccount.setText(accountSpHelper.getAccount());
            binder.includeLoginPassword.edtPassword.setText(accountSpHelper.getPassword());
        }

        //自动登录
        boolean auto = accountSpHelper.isAutoLogin();
        binder.includeLoginAuto.cbAuto.setChecked(auto);
        if (auto) doLogin();

        //        登录
        binder.includeLoginBtnlogin.btnLogin.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                doLogin();
            }
        });

        //注册
        binder.includeLoginBtnregister.btnRegister.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                accountSpHelper.putAccount(binder.includeLoginAccount.edtAccount.getText().toString().trim());
                ARouter.getInstance().build(Constant.REGISTER_ACTIVITY).navigation(context);
            }
        });
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        if (countDownTimer != null) countDownTimer.cancel();
        super.onDestroy();
    }

    private void doGetImageCode() {
        String acc = binder.includeLoginAccount.edtAccount.getText().toString().trim();
        String checkRes = CheckUtils.checkAccount(acc);
        if (checkRes != null) {
            binder.includeLoginAccount.edtAccount.requestFocus();
            binder.includeLoginAccount.edtAccount.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        presenter.getImageCode(acc);
        binder.includeLoginImageCode.edtImageCode.setText("");
        binder.includeLoginImageCode.edtImageCode.requestFocus();
    }

    private void doSendSmsCode() {
        String acc = binder.includeLoginAccount.edtAccount.getText().toString().trim();
        String checkRes = CheckUtils.checkAccount(acc);
        if (checkRes != null) {
            binder.includeLoginAccount.edtAccount.requestFocus();
            binder.includeLoginAccount.edtAccount.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        presenter.sendSmsCode(acc);
        binder.includeLoginSmsCode.edtSmsCode.setText("");
        binder.includeLoginSmsCode.edtSmsCode.requestFocus();
    }

    private void doLogin() {
        if (!binder.includeLoginAgree.cbAgreement.isChecked()) {
            SysToast.showToastShort(context, "请同意协议");
            return;
        }
        accountSpHelper.putAgree(true);

        accountSpHelper.putAutoLogin(binder.includeLoginAuto.cbAuto.isChecked());

        String acc = binder.includeLoginAccount.edtAccount.getText().toString().trim();
        String checkRes = CheckUtils.checkAccount(acc);
        if (checkRes != null) {
            binder.includeLoginAccount.edtAccount.requestFocus();
            binder.includeLoginAccount.edtAccount.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        String pwd = binder.includeLoginPassword.edtPassword.getText().toString().trim();
        checkRes = CheckUtils.checkPassword(pwd);
        if (checkRes != null) {
            binder.includeLoginPassword.edtPassword.requestFocus();
            binder.includeLoginPassword.edtPassword.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        String imgCode = binder.includeLoginImageCode.edtImageCode.getText().toString().trim();
        checkRes = CheckUtils.checkImageCode(imgCode);
        if (checkRes != null) {
            binder.includeLoginImageCode.edtImageCode.requestFocus();
            binder.includeLoginImageCode.edtImageCode.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        String smsCode = binder.includeLoginSmsCode.edtSmsCode.getText().toString().trim();
        checkRes = CheckUtils.checkImageCode(smsCode);
        if (checkRes != null) {
            binder.includeLoginSmsCode.edtSmsCode.requestFocus();
            binder.includeLoginSmsCode.edtSmsCode.setError(checkRes);
            SysToast.showToastShort(context, checkRes);
            return;
        }

        boolean remember = binder.includeLoginRemember.cbRemember.isChecked();
        if (remember) {
            accountSpHelper.putAccount(acc);
            accountSpHelper.putPassword(pwd);
        }
        accountSpHelper.putRemember(remember);

        presenter.login(acc);
    }

    @Override
    public void getImageCode(Bitmap bitmap) {
        binder.includeLoginImageCode.btnImageCode.setImageBitmap(bitmap);
    }

    private CountDownTimer countDownTimer;

    @Override
    public void sendSmsCodeSucc() {
        binder.includeLoginSmsCode.btnSmsCode.setClickable(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binder.includeLoginSmsCode.btnSmsCode.setText("剩余" + (millisUntilFinished / 1000 + 1) + "秒");
            }

            @Override
            public void onFinish() {
                sendSmsCodeFail();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void sendSmsCodeFail() {
        binder.includeLoginSmsCode.btnSmsCode.setClickable(true);
        binder.includeLoginSmsCode.btnSmsCode.setText("重新获取验证码");
    }

    @Override
    public void login() {
        SysToast.showToastShort(context, "登录成功");
    }

}
