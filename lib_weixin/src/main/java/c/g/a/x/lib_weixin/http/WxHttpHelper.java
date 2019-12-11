package c.g.a.x.lib_weixin.http;


import java.util.concurrent.TimeUnit;

import c.g.a.x.lib_support.android.utils.Logger;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WxHttpHelper {

    private final int connectTimeout = 8;
    private final int readTimeout = 8;
    private final int writeTimeout = 8;

    private final Retrofit retrofit;

//    private static WxHttpHelper mInstance;
//
//    public synchronized static WxHttpHelper getInstance() {
//        if (mInstance == null) mInstance = new WxHttpHelper();
//        return mInstance;
//    }

    public WxHttpHelper() {

        //
        //设置连接超时
        //设置从主机读信息超时
        //设置写信息超时
        OkHttpClient okHttpClient = new OkHttpClient.Builder()//
                .addInterceptor(new HttpLoggingInterceptor(Logger::e
                ).setLevel(HttpLoggingInterceptor.Level.BODY))
                //设置连接超时
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                //设置从主机读信息超时
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                //设置写信息超时
                .writeTimeout(writeTimeout, TimeUnit.SECONDS).build();

        String baseUrl = "https://api.weixin.qq.com/sns/";
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                //如果网络访问返回的字符串，而不是json数据格式，可以使用ScalarsConverterFactory转换器
//                .addConverterFactory(ScalarsConverterFactory.create())
                //支持对象转json串
                .addConverterFactory(GsonConverterFactory.create())
                //为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }


    public HttpService createService() {
        return retrofit.create(HttpService.class);
    }

    public final Observable<WxAccessTokenResponse> accessToken(String appid, String secret, String code, String grant_type) {
        return createService().accessToken(appid, secret, code, grant_type);
    }

    public final Observable<ResponseBody> userinfo(String access_token, String openid) {
        return createService().userinfo(access_token, openid);
    }

}

