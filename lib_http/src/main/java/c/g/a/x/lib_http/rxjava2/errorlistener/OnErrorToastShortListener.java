package c.g.a.x.lib_http.rxjava2.errorlistener;

import android.widget.Toast;

import c.g.a.x.lib_http.rxjava2.filterobserver.BaseFilter;


/**
 * Created by Administrator on 2018/1/9.
 * 当发生错误时弹出toast 短时间
 */

public class OnErrorToastShortListener implements BaseFilter.OnErrorListener {

    @Override
    public void onDo(BaseFilter.Error error) {
        Toast.makeText(error.context, error.msg, Toast.LENGTH_SHORT).show();
    }
}