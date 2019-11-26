package c.g.a.x.global_application;

import c.g.a.x.lib_http.application.HttpHelper;

/**
 * Created by Administrator on 2018/5/8.
 */

public final class UserInfo {

    public static UserInfo getInstance() {
        return InnerInstance.INSTANCE;
    }


    private static class InnerInstance {
        private static UserInfo INSTANCE = new UserInfo();
    }

    private UserInfo() {
    }

    public boolean isLogin() {
        return false;
    }

    public void clear() {
//        this.userId = null;
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
