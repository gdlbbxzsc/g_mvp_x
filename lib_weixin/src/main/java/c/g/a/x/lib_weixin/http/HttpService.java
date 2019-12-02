package c.g.a.x.lib_weixin.http;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpService {

    @GET("oauth2/access_token")
    Observable<WxAccessTokenResponse> accessToken(@Query("appid") String appid,
                                                  @Query("secret") String secret,
                                                  @Query("code") String code,
                                                  @Query("grant_type") String grant_type);

    @GET("userinfo")
    Observable<ResponseBody> userinfo(@Query("access_token") String access_token,
                                      @Query("openid") String openid);


}
