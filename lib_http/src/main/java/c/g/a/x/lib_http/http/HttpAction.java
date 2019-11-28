package c.g.a.x.lib_http.http;


import android.content.Context;
import android.util.ArrayMap;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import c.g.a.x.lib_http.annotation.FieldOrder;
import c.g.a.x.lib_http.base.BaseRequest;
import c.g.a.x.lib_http.base.GetUrlPathRequest;
import c.g.a.x.lib_http.constant.Constant;
import c.g.a.x.lib_http.rxjava2.Rx2Helper;
import c.g.a.x.lib_http.rxjava2.filterobserver.BaseObserver;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class HttpAction {

    Context context;

    Observable observable;

    HttpService service = HttpMgr.createService();

    public static final HttpAction context(Context context) {
        return new HttpAction(context);
    }

    private HttpAction(Context context) {
        this.context = context;
    }

    public HttpAction urlStreaming(String url) {
        observable = service.urlStreaming(url);
        observable = Rx2Helper.applySchedulers(observable);
        observable = Rx2Helper.addNetWorkCheck(observable, context);
        return this;
    }

    public HttpAction url(String url) {
        observable = service.url(url);
        observable = Rx2Helper.applySchedulers(observable);
        observable = Rx2Helper.addNetWorkCheck(observable, context);
        return this;
    }


    public final HttpAction post(BaseRequest request) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(request));

            creatObservable(request.getRequestPath(), body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(BaseRequest request) {
        try {
            creatObservable(request.getRequestPath(), toMap(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(GetUrlPathRequest request) {
        try {
            creatObservable(request.getRequestPath(), joinUrlPath(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private <O extends Object> void creatObservable(String methodName, O o) throws Exception {

        Method method = service.getClass().getMethod(methodName, o.getClass());
        observable = (Observable) method.invoke(service, o);
        observable = Rx2Helper.applySchedulers(observable);
        observable = Rx2Helper.addNetWorkCheck(observable, context);

    }

    public Map<String, Object> toMap(BaseRequest request) {

        Field[] fields = request.getClass().getFields();

        Map<String, Object> map = new ArrayMap<>();

        for (Field field : fields) {
            try {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (isStatic) continue;

                Object v = field.get(this);
                if (v == null) continue;

                String k = field.getName();
                map.put(k, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private String joinUrlPath(BaseRequest request) throws Exception {

        Field[] fields = request.getClass().getFields();

        if (fields == null || fields.length <= 0) return "";


        Arrays.sort(fields, new FieldComparator());
        StringBuffer sb = new StringBuffer();
        for (Field f : fields) {
            if (f.getAnnotation(FieldOrder.class) == null)
                throw new Exception(f.getName() + "need to annotate at head like @FieldOrder(order = 1)");

            sb.append(Constant.Data.JOIN_SEPARATOR);
            sb.append(f.get(request));
        }

        return sb.substring(Constant.Data.JOIN_SEPARATOR.length());
    }

    private class FieldComparator implements Comparator<Field> {
        public int compare(Field o1, Field o2) {
            int fst = o1.getAnnotation(FieldOrder.class).order();
            int scd = o2.getAnnotation(FieldOrder.class).order();
            if (fst < scd) return -1;
            if (fst > scd) return 1;
            return 0;
        }
    }

    public final HttpAction progress() {
        observable = Rx2Helper.addProgress(observable, context);
        return this;
    }

    public final Observable toObservable() {
        return observable;
    }

    public final <O extends BaseObserver> void subscribe(O o) {
        observable.subscribe(o);
    }


}