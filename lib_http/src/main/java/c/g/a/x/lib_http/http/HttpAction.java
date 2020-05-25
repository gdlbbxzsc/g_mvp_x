package c.g.a.x.lib_http.http;


import android.content.Context;
import android.util.ArrayMap;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import c.g.a.x.lib_http.annotation.FieldOrder;
import c.g.a.x.lib_http.base.BaseRequest;
import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.base.GetUrlPathRequest;
import c.g.a.x.lib_http.constant.Constant;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class HttpAction<R extends BaseResponse> extends BaseAction<R> {

    public static <S extends BaseResponse> HttpAction<S> context(Context context) {
        return new HttpAction<>(context);
    }

    protected HttpAction(Context context) {
        super(context);
    }

    public final HttpAction post(BaseRequest<R> request) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(request));

            createObservable(request.getRequestPath(), body, RequestBody.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(BaseRequest<R> request) {
        try {
            createObservable(request.getRequestPath(), createQueryMap(request), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(GetUrlPathRequest<R> request) {
        try {
            createObservable(request.getRequestPath(), createUrlPath(request), String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private <O> void createObservable(String methodName, O o, Class clzType) throws Exception {
        //这里出现了一个bug 如果o类型为RequestBody 最新版本里面o.getClass() 无法获取到正确的 RequestBody 类型 因为是kotlin编写的原因未知
//        所以这里暂时这样写
//        Method method = service.getClass().getMethod(methodName, o.getClass());
        Method method = service.getClass().getMethod(methodName, clzType);
        observable = (Observable<R>) method.invoke(service, o);
        commonSchedulers();
    }

    private Map<String, Object> createQueryMap(BaseRequest request) {

        Field[] fields = request.getClass().getFields();

        Map<String, Object> map = new ArrayMap<>();

        for (Field field : fields) {
            try {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) continue;

                Object v = field.get(request);
                if (v == null) continue;

                String k = field.getName();
                map.put(k, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private String createUrlPath(BaseRequest request) throws Exception {

        Field[] fields = request.getClass().getFields();

        if (fields.length <= 0) return "";

        Arrays.sort(fields, (o1, o2) -> Integer.compare(o1.getAnnotation(FieldOrder.class).order(), o2.getAnnotation(FieldOrder.class).order()));
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            if (f.getAnnotation(FieldOrder.class) == null)
                throw new Exception(f.getName() + "need to annotate at head like @FieldOrder(order = 1)");

            sb.append(Constant.Data.JOIN_SEPARATOR);
            sb.append(f.get(request));
        }

        return sb.substring(Constant.Data.JOIN_SEPARATOR.length());
    }

//    public Flowable<Upload2ossResponse> upload2oss(String path, String fileType) {
//
//        File file = new File(path);
//        String fileName = file.getName();
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//        builder.addFormDataPart("file", file.getName(), fileBody);
//
//
//        builder.addFormDataPart("fileType", fileType);
//        builder.addFormDataPart("suffixName", suffixName);
//        /////////
//        builder.setType(MultipartBody.FORM);
//
//        MultipartBody multipartBody = builder.build();
//
//        return applySchedulers(HttpClient.getHttpService().upload2oss(multipartBody));
//    }
//
//
//    public Flowable<ResponseBody> uploadFiles(ImageItem items) {
//        File file = new File(items.path);
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", items.name, fileBody);
//        return applySchedulers(HttpClient.getHttpService().uploadFiles(body));
//    }
}
