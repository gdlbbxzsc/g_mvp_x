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

public final class HttpAction1<R extends BaseResponse> extends BaseAction<R> {

    public static <S extends BaseResponse> HttpAction1<S> context(Context context) {
        return new HttpAction1<>(context);
    }

    protected HttpAction1(Context context) {
        super(context);
    }

    public final HttpAction1 post(BaseRequest<R> request) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(request));

            createObservable(request.getRequestPath(), body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction1 get(BaseRequest<R> request) {
        try {
            createObservable(request.getRequestPath(), createQueryMap(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction1 get(GetUrlPathRequest<R> request) {
        try {
            createObservable(request.getRequestPath(), createUrlPath(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private <O> void createObservable(String methodName, O o) throws Exception {
        Method method = service.getClass().getMethod(methodName, o.getClass());
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
}
