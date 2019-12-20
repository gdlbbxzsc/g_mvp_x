package c.g.a.x.lib_http.base;


import java.io.InputStream;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import c.g.a.x.lib_base.BaseApplication;
import c.g.a.x.lib_http.interceptor.HeadTokenInterceptor;
import c.g.a.x.lib_http.interceptor.LogoutInterceptor;
import c.g.a.x.lib_support.android.utils.Logger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseHttpClient {

    private String baseUrl = "";

    private int connectTimeout = 8;
    private int readTimeout = 8;
    private int writeTimeout = 8;

    private final Retrofit retrofit;

    public BaseHttpClient() {

        baseUrl = getBaseUrl();

        connectTimeout = getConnectTimeout();
        readTimeout = getReadTimeout();
        writeTimeout = getWriteTimeout();


        OkHttpClient okHttpClient = initOkHttpClient();

        retrofit = initRetrofit(okHttpClient);

    }


    private OkHttpClient initOkHttpClient() {
        SSLSocketFactory sslSocketFactory = null;
        TrustManager[] trustManagers = null;

        try {
            InputStream is = BaseApplication.getInstances().getAssets().open("server.cer");
            //获取相关类型证书
            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(is);
            //设置秘钥等
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("tcs_certificate", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            trustManagers = trustManagerFactory.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder//
                .addInterceptor(new HttpLoggingInterceptor(message ->
                        Logger.e(message)
                ).setLevel(HttpLoggingInterceptor.Level.BODY))

                //通用token
                .addInterceptor(new HeadTokenInterceptor())

                // 登录异常拦截器
                .addInterceptor(new LogoutInterceptor())

                //设置连接超时
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                //设置从主机读信息超时
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                //设置写信息超时
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                //禁止代理
                .proxy(Proxy.NO_PROXY)
                //                  验证服务器信息是否可信
                .hostnameVerifier((s, sslSession) -> true);

        //设置ssl证书
        if (sslSocketFactory != null)
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0]);

        builder = initOkHttpBuilder(builder);

        return builder.build();
    }


    private Retrofit initRetrofit(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl)
                .client(okHttpClient)
                //如果网络访问返回的字符串，而不是json数据格式，可以使用ScalarsConverterFactory转换器
//                .addConverterFactory(ScalarsConverterFactory.create())
                //支持对象转json串
                .addConverterFactory(GsonConverterFactory.create())
                //为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder = initRetrofitBuilder(builder);

        return builder.build();
    }

    public <T> T createService(Class<T> cls) {
        return retrofit.create(cls);
    }

    protected abstract String getBaseUrl();

    protected int getConnectTimeout() {
        return connectTimeout;
    }

    protected int getReadTimeout() {
        return readTimeout;
    }

    protected int getWriteTimeout() {
        return writeTimeout;
    }

    protected Retrofit.Builder initRetrofitBuilder(Retrofit.Builder builder) {
        return builder;
    }

    protected OkHttpClient.Builder initOkHttpBuilder(OkHttpClient.Builder builder) {
        return builder;
    }
}

