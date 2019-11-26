package c.g.a.x.lib_http.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import c.g.a.x.lib_http.application.HttpHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by v on 2019/8/8.
 */
public class HeadTokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken;
        if (TextUtils.isEmpty(HttpHelper.getInstance().accessToken))
            accessToken = "";
        else
            accessToken = HttpHelper.getInstance().accessToken;
        Request request = chain.request().newBuilder().addHeader("AccessToken", accessToken).build();
        return chain.proceed(request);
    }

}
