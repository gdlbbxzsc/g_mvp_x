package c.g.a.x.lib_support.views.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import c.g.a.x.lib_support.R;
import c.g.a.x.lib_support.android.utils.Logger;

/**
 * Created by Administrator on 2018/3/12.
 */

public final class WaitDialogMnger {

    private final Map<Context, ProgressDialog> contextMap = new HashMap<>(2);
    private final Map<ProgressDialog, Integer> countMap = new HashMap<>(2);


    public static WaitDialogMnger getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final WaitDialogMnger INSTANCE = new WaitDialogMnger();
    }

    private WaitDialogMnger() {
    }

    public void show(Context context) {
        show(context, "请稍后...");
    }

    public synchronized void show(Context context, String text) {

        ProgressDialog dialog = contextMap.get(context);
        if (dialog == null) {
            dialog = makeDialog(context);
            dialog.show();
            contextMap.put(context, dialog);
        }
        dialog.setMessage(text);

        int count = scaleUseTimes(dialog, true);
        Logger.e(context + " show dialog====>" + count);
//        if (!dialog.isShowing()) dialog.show();
    }

    public synchronized void cancel(Context context) {
        Logger.e(context + " cancel dialog 1====>");
        ProgressDialog dialog = contextMap.get(context);
        if (dialog == null) return;

        int count = scaleUseTimes(dialog, false);
        Logger.e(context + " cancel dialog 2====>" + count);
        if (count > 0) return;

//        contextMap.put(context, null);
        contextMap.remove(context);

        if (!dialog.isShowing()) return;

        dialog.cancel();
    }

    public synchronized void clear(Context context) {
        ProgressDialog dialog = contextMap.get(context);
        if (dialog == null) return;

        countMap.remove(dialog);
        contextMap.remove(context);

        dialog.cancel();
    }


    private int scaleUseTimes(ProgressDialog object, boolean isAdd) {
        Integer count = countMap.get(object);
        if (count == null) count = 0;

        count += isAdd ? 1 : -1;

        if (count <= 0) {
            count = 0;
            countMap.remove(object);
        } else {
            countMap.put(object, count);
        }

        return count;
    }

    private WaitDialog1 makeDialog(Context context) {
        return new WaitDialog1(context);
    }


    public class WaitDialog extends ProgressDialog {

        private WaitDialog(Context context) {
            super(context, ProgressDialog.THEME_HOLO_LIGHT);
            this.setCancelable(false);
        }
    }

    public class WaitDialog1 extends ProgressDialog {

        private WaitDialog1(Context context) {
            super(context, R.style.WaitDialogTheme);
            this.setCancelable(false);

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            init(getContext());
        }

        private void init(Context context) {
            setCancelable(true);
            setCanceledOnTouchOutside(false);

            setContentView(R.layout.loading);//loading的xml文件
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(params);
        }

    }


//       super(context, ProgressDialog.THEME_HOLO_LIGHT);
//        this.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
//        this.setCancelable(false);
//        this.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        this.setMax(100);
//        this.setMessage(msg);
}
