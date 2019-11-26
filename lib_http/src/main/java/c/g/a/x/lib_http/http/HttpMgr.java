package c.g.a.x.lib_http.http;


import c.g.a.x.lib_http.base.BaseHttpClient;
import c.g.a.x.lib_http.constant.Constant;

public class HttpMgr extends BaseHttpClient {

    @Override
    public String getBaseUrl() {
        return Constant.URL.BASE_URL;
    }

    public static HttpService createService() {
        return getInstance().createService(HttpService.class);
    }

    public static HttpMgr getInstance() {
        return InnerInstance.INSTANCE;
    }


    // 构建全局Retrofit客户端
    private static final class InnerInstance {
        private static final HttpMgr INSTANCE = new HttpMgr();
    }

}
