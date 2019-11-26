package c.g.a.x.lib_http.rxjava2.errorlistener;

import android.app.AlertDialog;

import c.g.a.x.lib_http.rxjava2.filterobserver.BaseFilter;


/**
 * Created by Administrator on 2018/1/9.
 * 当发生错误时候主动弹窗 这个只是一个示例和通用弹窗，
 */

public class OnErrorDialogListener implements BaseFilter.OnErrorListener {

    @Override
    public void onDo(BaseFilter.Error error) {
        new AlertDialog.Builder(error.context).show();
    }
}