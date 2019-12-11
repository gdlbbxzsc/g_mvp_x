package c.g.a.x.lib_http.http;


import android.content.Context;
import android.util.ArrayMap;

import com.google.gson.Gson;

import java.io.File;
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
import c.g.a.x.lib_support.android.utils.AlbumNotifyHelper;
import c.g.a.x.lib_support.android.utils.FileHelper;
import c.g.a.x.lib_support.utils.FileUtils;
import c.g.a.x.lib_support.utils.IOUtils;
import c.g.a.x.lib_support.views.toast.SysToast;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/12/4.
 */

public final class HttpAction {

    final Context context;

    Observable observable;

    final HttpService service = HttpMgr.createService();

    public static HttpAction context(Context context) {
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

            createObservable(request.getRequestPath(), body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(BaseRequest request) {
        try {
            createObservable(request.getRequestPath(), toMap(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public final HttpAction get(GetUrlPathRequest request) {
        try {
            createObservable(request.getRequestPath(), joinUrlPath(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private <O> void createObservable(String methodName, O o) throws Exception {

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

    private String joinUrlPath(BaseRequest request) throws Exception {

        Field[] fields = request.getClass().getFields();

        if (fields.length <= 0) return "";


        Arrays.sort(fields, new FieldComparator());
        StringBuilder sb = new StringBuilder();
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
            return Integer.compare(o1.getAnnotation(FieldOrder.class).order(), o2.getAnnotation(FieldOrder.class).order());
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


    public final void download(Context context, String url) {
        Observable<ResponseBody> observable = HttpAction.context(context).url(url).toObservable();
        observable.observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(body -> {
                    File file = FileHelper.getInstance().toCameraPath().fileName(FileUtils.makeNameByMD5(url)).create();
                    IOUtils.writeData2File(body.byteStream(), file);
                    AlbumNotifyHelper.getInstance(context).insertPhoto(file.getAbsolutePath()).notifyByBroadcast();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        body -> SysToast.showToastShort(context, "下载成功"),
                        throwable -> {
                            throwable.printStackTrace();
                            SysToast.showToastShort(context, "下载失败");
                        });
    }
}
