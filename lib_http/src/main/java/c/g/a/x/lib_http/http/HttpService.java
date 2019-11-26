package c.g.a.x.lib_http.http;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpService {

    //不要删除部分 start--------------

    @Streaming
    @GET
    Observable<ResponseBody> urlStreaming(@Url String fileUrl);

    @GET
    Observable<ResponseBody> url(@Url String fileUrl);

//    //不要删除部分 end--------------

//    @Body       多用于Post请求发送非表达数据，根据转换方式将实例对象转化为对应字符串传递参数，比如使用Post发送Json数据，添加GsonConverterFactory则是将body转化为json字符串进行传递
//    @Filed      多用于Post方式传递参数，需要结合@FromUrlEncoded使用，即以表单的形式传递参数
//    @FiledMap   多用于Post请求中的表单字段，需要结合@FromUrlEncoded使用
//    @Part       用于表单字段，Part和PartMap与@multipart注解结合使用，适合文件上传的情况
//    @PartMap    用于表单字段，默认接受类型是Map<String, RequestBody>，可用于实现多文件上传
//    @Path       用于Url中的占位符
//    @Query	  用于Get请求中的参数
//    @QueryMap   与Query类似，用于不确定表单参数
//    @Url	      指定请求路径

//    @FromUrlCoded 表示请求发送编码表单数据，每个键值对需要使用@Filed注解
//    @Multipart    表示请求发送form_encoded数据(使用于有文件上传的场景)，每个键值对需要用@Part来注解键名，随后的对象需要提供值
//    @Streaming    表示响应用字节流的形式返回，如果没有使用注解，默认会把数据全部载入到内存中，该注解在下载大文件时特别有用


//    @FormUrlEncoded
//    @POST("moblieLogin.htm")
//    Observable<BaseResponse> moblieLogin(@FieldMap Map<String, Object> map);


//      如果用的 GetUrlPathRequest 要这样标注@Path(value = "path", encoded = true)
//    @GET("tlcuser/login/sendLoginSMSValidateCode/{path}")
//    Observable<SendLoginSMSValidateCodeResponse> sendLoginSMSValidateCode(@Path(value = "path", encoded = true) String path);

}
