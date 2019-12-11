package c.g.a.x.lib_sp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import c.g.a.x.lib_sp.base.BaseSpHelper;


/**
 * Created by Administrator on 2018/7/9.
 */

public class SpDefaultHelper extends BaseSpHelper {

    public SpDefaultHelper(Context context, String sp_name) {
        super(context, sp_name);
    }

    public void putAccountPassword(String account, String password) {

        if (TextUtils.isEmpty(account)) account = "";
        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = getSp().edit();//
        editor.putString("account", account);//
        editor.putString("password", password);//
        editor.apply();
    }

    public void putPassword(String password) {

        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = getSp().edit();//
        editor.putString("password", password);//
        editor.apply();
    }

    public String getAccount() {
        return getSp().getString("account", "");
    }

    public String getPassword() {
        return getSp().getString("password", "");
    }

    //
    public void putRemember(boolean isRemember) {
        SharedPreferences.Editor editor = getSp().edit();//
        editor.putBoolean("isRemember", isRemember);//
        editor.apply();
    }

    public boolean isRemember() {
        return getSp().getBoolean("isRemember", false);
    }

    //
    public void putUserId(int userId) {
        SharedPreferences.Editor editor = getSp().edit();//
        editor.putInt("userId", userId);//
        editor.apply();
    }

    public int getUserId() {
        return getSp().getInt("userId", 0);
    }


    public void putFirstUse(boolean firstUse) {
        SharedPreferences.Editor editor = getSp().edit();//
        editor.putBoolean("FirstUse", firstUse);//
        editor.apply();
    }

    public boolean isFirstUse() {
        return getSp().getBoolean("FirstUse", true);
    }
}
