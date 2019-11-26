package c.g.a.x.lib_http.interceptor;

import java.io.IOException;

import c.g.a.x.lib_support.android.utils.Logger;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by v on 2019/8/8.
 */
public class LogoutInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Logger.e("=========>LogoutInterceptor ");
        //TODO GDL
//        if (true)
        return chain.proceed(chain.request());

//        Request request = chain.request();
//        Response response = chain.proceed(request);
//
//
//        MediaType mediaType = response.body().contentType();
//        Logger.e(mediaType);
//        if (mediaType == null) return response;
//
//        //这行一定要写在这里 否则部分流数据 无法正确返回 如图片无法正常的解析
//        if (!StringUtils.isEqual(mediaType.subtype(), "json")) return response;
//
//        String content = response.body().string();
//        response = response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
//
//
//        BaseResponse baseResponse = new Gson().fromJson(content, BaseResponse.class);
//        if (baseResponse.getCode() != 411) return response;
//
//        // 第二次进行请求
//        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
//        refreshTokenRequest.refreshToken = HttpHelper.getInstance().refreshToken;
//
//        retrofit2.Response<RefreshTokenResponse> refreshTokenResponse = HttpAction1.getInstance().get(refreshTokenRequest).subscribe();
//        if (refreshTokenResponse.body().getCode() != 412) {
//            RefreshTokenResponse.DataBean dataBean = refreshTokenResponse.body().getData();
//            String access_token = dataBean.getAccess_token();
//            String refresh_token = dataBean.getRefresh_token();
//
//            SpMnger.getDefaultHelper().putAccessToken(access_token);
//            SpMnger.getDefaultHelper().putRefreshToken(refresh_token);
//
//            HttpHelper.getInstance().accessToken = access_token;
//            HttpHelper.getInstance().refreshToken = refresh_token;
//
//            // 获取最新token 对请求头token 进行更改再次进行请求
//            Request newRequest = request.newBuilder().removeHeader("AccessToken").addHeader("AccessToken", access_token).build();
//            Response newResponse = chain.proceed(newRequest);
//            mediaType = newResponse.body().contentType();
//            content = newResponse.body().string();
//            newResponse = newResponse.newBuilder().body(ResponseBody.create(mediaType, content)).build();
//            return newResponse;
//        }
//        return response;

    }

}
