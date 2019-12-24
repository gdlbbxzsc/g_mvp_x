package c.g.a.x.global_application;

import c.g.a.x.lib_http.application.HttpHelper;

/**
 * Created by Administrator on 2018/5/8.
 */

public final class UserInfo {

    UserInfo() {
    }

    boolean isLogin() {
        return false;
    }

    public void clear() {
//        this.userId = 0;
//        this.token = null;

        HttpHelper.getInstance().accessToken = null;
//        SpHelper.getInstance().setUserID(null);
//        SpHelper.getInstance().setToken(null);
    }

    public void setSession(String userId, String token) {
//        this.userId = userId;
//        this.token = token;
//        SpHelper.getInstance().setUserID(userId);
//        SpHelper.getInstance().setToken(token);
    }

    public void getCookie() {
//        userId = SpHelper.getInstance().getUserid();
//        token = SpHelper.getInstance().getToken();
    }
    ////////////////////////////
}
