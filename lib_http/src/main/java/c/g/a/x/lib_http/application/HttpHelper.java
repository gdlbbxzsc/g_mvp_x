package c.g.a.x.lib_http.application;

/**
 * Created by Administrator on 2019/9/6.
 */

public final class HttpHelper {

    public static HttpHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final HttpHelper INSTANCE = new HttpHelper();
    }

    private HttpHelper() {
    }

    public String accessToken;
}
