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

    public boolean putAccountPassword(String account, String password) {

        if (TextUtils.isEmpty(account)) account = "";
        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = getSp().edit();//
        editor.putString("account", account);//
        editor.putString("password", password);//
        return editor.commit();
    }

    public boolean putPassword(String password) {

        if (TextUtils.isEmpty(password)) password = "";

        SharedPreferences.Editor editor = getSp().edit();//
        editor.putString("password", password);//
        return editor.commit();
    }

    public String getAccount() {
        return getSp().getString("account", "");
    }

    public String getPassword() {
        return getSp().getString("password", "");
    }

    //
    public boolean putRemember(boolean isRemember) {
        SharedPreferences.Editor editor = getSp().edit();//
        editor.putBoolean("isRemember", isRemember);//
        return editor.commit();
    }

    public boolean isRemember() {
        return getSp().getBoolean("isRemember", false);
    }

    //
    public boolean putUserId(int userId) {
        SharedPreferences.Editor editor = getSp().edit();//
        editor.putInt("userId", userId);//
        return editor.commit();
    }

    public int getUserId() {
        return getSp().getInt("userId", 0);
    }
}
