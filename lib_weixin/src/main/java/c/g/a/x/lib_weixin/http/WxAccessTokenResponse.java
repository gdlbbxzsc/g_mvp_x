package c.g.a.x.lib_weixin.http;

/**
 * Created by Administrator on 2018/2/1.
 */

public class WxAccessTokenResponse {

    /**
     * access_token : 6_eyyWwd63d-nTT_lkrZMcObZ0Z9Vb6SszDxEphopV-XDLn14rwvl7-nQs0xSUlUaRHSLvkzHmItSzewQq7HACkYOuI4_BBJyM7oxUaTJOpSQ
     * expires_in : 7200
     * refresh_token : 6_vOeSS1tk2JRwQp3POzlSKw4WqnFxWjWmFoK67Pkvs2bQYzrsgMVRPQdOi8iIiVYf2IlGstVrIIuHMfZCAVeBIdg2iHykU6IP7xm0oqpPxhc
     * openid : oKduZv9i7eYTVbZz7efUai507iUg
     * scope : snsapi_userinfo
     * unionid : omDvEwzx1pWr5_Ps2dX369oRKi20
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
