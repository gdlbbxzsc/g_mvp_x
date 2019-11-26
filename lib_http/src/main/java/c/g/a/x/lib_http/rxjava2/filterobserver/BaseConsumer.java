package c.g.a.x.lib_http.rxjava2.filterobserver;

import android.content.Context;
import android.widget.Toast;


import c.g.a.x.lib_http.base.BaseResponse;
import c.g.a.x.lib_http.rxjava2.errorlistener.OnErrorToastShortListener;
import io.reactivex.functions.Consumer;


public abstract class BaseConsumer<T extends BaseResponse> extends BaseFilter implements Consumer<T> {


    protected boolean filterAllError;

    public BaseConsumer(Context context) {
        this(context, false);
    }

    public BaseConsumer(Context context, boolean filterAllError) {
        super(context);

        this.filterAllError = filterAllError;

        putDataError(300, "系统错误", OnErrorToastShortListener.class);
        putDataError(408, "oss信息为空，请联系管理员。", OnErrorToastShortListener.class);
        putDataError(409, "参数错误", OnErrorToastShortListener.class);
        putDataError(410, "签名错误", OnErrorToastShortListener.class);
        putDataError(411, "token错误", OnErrorToastShortListener.class);
        putDataError(412, "签名或token错误", OnErrorToastShortListener.class);
    }

    @Override
    public final void accept(T vo) throws Exception {
        try {
            boolean b = filterDataError(vo);
            if (!b) {
                return;
            }
            if (filterAllError && vo.getCode() != 200) {
                Toast.makeText(getContext(), vo.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            onDo(vo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            finalDo();
        }
    }


    public abstract void onDo(T vo) throws Exception;


    public void finalDo() throws Exception {
    }
}
