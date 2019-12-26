package c.g.a.x.module_login;

import android.text.TextUtils;

import c.g.a.x.lib_support.utils.StringUtils;

public final class CheckUtils {
    public static final String checkAccount(String account) {
        if (TextUtils.isEmpty(account)) return "请输入手机号";
        if (!StringUtils.isMobile(account)) return "请输入正确的手机号";
        return null;
    }

    public static final String checkPassword(String password) {
        if (TextUtils.isEmpty(password)) return "请输入密码";
        if (password.length() < 6) return "请输入正确的密码";
        return null;
    }

    public static final String checkImageCode(String imageCode) {
        if (TextUtils.isEmpty(imageCode)) return "请输入图形验证码";
        if (imageCode.length() != 4) return "请输入正确的图形验证码";
        return null;
    }

    public static final String checkSmsCode(String verificationCode) {
        if (TextUtils.isEmpty(verificationCode)) return "请输入短信验证码";
        if (verificationCode.length() != 6) return "请输入正确的短信验证码";
        return null;
    }
}
