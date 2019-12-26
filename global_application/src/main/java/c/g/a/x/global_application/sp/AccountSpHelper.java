package c.g.a.x.global_application.sp;

import android.text.TextUtils;

public final class AccountSpHelper extends BaseSpHelper {

    ////////////////
    public final void putAccount(String account) {
        if (TextUtils.isEmpty(account)) return;
        sp.edit().putString("account", account).apply();
    }

    public final String getAccount() {
        return sp.getString("account", "");
    }

    ////////////////
    public final void putPassword(String password) {
        if (TextUtils.isEmpty(password)) return;
        sp.edit().putString("password", password).apply();
    }

    public final String getPassword() {
        return sp.getString("password", "");
    }

    ////////////////
    public void putRemember(boolean remember) {
        sp.edit().putBoolean("remember", remember).apply();
    }

    public boolean isRemember() {
        return sp.getBoolean("remember", false);
    }

    ////////////////
    public void putAutoLogin(boolean autoLogin) {
        sp.edit().putBoolean("autoLogin", autoLogin).apply();
    }

    public boolean isAutoLogin() {
        return sp.getBoolean("autoLogin", false);
    }

    ////////////////
    public void putUserId(int userId) {
        sp.edit().putInt("userId", userId).apply();
    }

    public int getUserId() {
        return sp.getInt("userId", 0);
    }

    ////////////////
    public void putToken(String token) {
        sp.edit().putString("token", token).apply();
    }

    public String getToken() {
        return sp.getString("token", "");
    }

    ////////////////
    public boolean isFirstUse() {
        return sp.getBoolean("firstUse", true);
    }

    public void closeFirstUse() {
        sp.edit().putBoolean("firstUse", false).apply();
    }

    ////////////////
    public void putAgree(boolean agree) {
        sp.edit().putBoolean("agree", agree).apply();
    }

    public boolean isAgree() {
        return sp.getBoolean("agree", false);
    }
}
