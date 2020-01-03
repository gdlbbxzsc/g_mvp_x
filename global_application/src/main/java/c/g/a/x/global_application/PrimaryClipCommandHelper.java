package c.g.a.x.global_application;

import android.content.ClipboardManager;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import c.g.a.x.lib_support.android.utils.AndroidUtils;
import c.g.a.x.lib_support.android.utils.Logger;
import c.g.a.x.lib_support.base.BaseApplication;
import c.g.a.x.lib_support.utils.StringUtils;

public final class PrimaryClipCommandHelper implements LifecycleObserver {

    public static PrimaryClipCommandHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final PrimaryClipCommandHelper INSTANCE = new PrimaryClipCommandHelper();
    }

    private volatile boolean first;

    private PrimaryClipCommandHelper() {
        AndroidUtils.addPrimaryClipChangedListener(BaseApplication.getInstances(), onPrimaryClipChangedListener);
        first = true;
    }

    private Lifecycle lifecycle;

    private OnChangedListener onChangedListener;

    public final void changeActivity(AppCompatActivity activity, OnChangedListener onChangedListener) {

        this.onChangedListener = onChangedListener;

        if (lifecycle != null) lifecycle.removeObserver(this);
        this.lifecycle = activity.getLifecycle();
        this.lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public final void resume() {
        String todoStr = (String) GlobalApplication.getDataMapData("todo_primaryclip");
        if (first || !TextUtils.isEmpty(todoStr)) {
            onPrimaryClipChangedListener.onPrimaryClipChanged();
            first = false;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void destroy() {
        if (lifecycle != null) lifecycle.removeObserver(this);
    }


    public final void unregister() {
        AndroidUtils.removePrimaryClipChangedListener(BaseApplication.getInstances(), onPrimaryClipChangedListener);
    }

    //自己的口令记录一下和剪切板进行对比时不处理
    public final void recordMyCommand(String str) {
        GlobalApplication.setDataMapData("my_primaryclip", str);
    }

    private final ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {

        @Override
        public void onPrimaryClipChanged() {
            // 获取剪贴板数据
            String primaryClipText = AndroidUtils.getPrimaryClipText(BaseApplication.getInstances());
            Logger.e("onPrimaryClipChanged primaryClipText===>", primaryClipText);
            if (TextUtils.isEmpty(primaryClipText)) return;

//            自己发布的口令不进行处理
            String mine = (String) GlobalApplication.getDataMapData("my_primaryclip");
            if (StringUtils.isEqual(primaryClipText, mine)) return;

            //未登录口令不进行处理，但保留处理权利
            if (!GlobalApplication.getInstances().checkLogin()) {
                GlobalApplication.setDataMapData("todo_primaryclip", "yes");
                return;
            }

//            不在当前界面不进行处理
            if (lifecycle == null || !lifecycle.getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                GlobalApplication.setDataMapData("todo_primaryclip", "yes");
                return;
            }
            AndroidUtils.copyText(BaseApplication.getInstances(), "");
            onChangedListener.onChanged(primaryClipText);
        }
    };

    public interface OnChangedListener {
        void onChanged(String primaryClipText);
    }
}
