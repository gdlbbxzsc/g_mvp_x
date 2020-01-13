package c.g.a.x.lib_support.views.dialog;

import android.app.Dialog;

import java.util.LinkedList;
import java.util.List;

public final class DialogQueueHelper<T extends Dialog> {

    private final LinkedList<T> dialogQueue = new LinkedList<>();

    private volatile T currentDialog = null;//当前显示的Dialog

    private volatile boolean pause;

    public static DialogQueueHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final DialogQueueHelper INSTANCE = new DialogQueueHelper();
    }

    private DialogQueueHelper() {
    }

    public final void addDialog(List<T> dialogs) {
        for (T dialog : dialogs) {
            if (dialog == null) continue;
            dialogQueue.offer(dialog);
        }
    }

    public final void addDialog(T dialog) {
        if (dialog != null) dialogQueue.offer(dialog);
    }

    public final void addDialogFirst(T dialog) {
        if (dialog != null) dialogQueue.offerFirst(dialog);
    }

    public final void show() {

        if (pause) {
            pause = false;
            return;
        }

        if (currentDialog != null) return;

        //从队列中拿出一个Dialog实例,并从列表中移除
        currentDialog = dialogQueue.poll();

        //当队列为空的时候拿出来的会是null
        if (currentDialog == null) return;

        currentDialog.show();
        currentDialog.setOnDismissListener(dialog -> {
            //这边设置了dismiss监听,在监听回调中再次调用show方法,可以获取下一个弹窗
            currentDialog = null;
            show();
        });
    }

    public final void pause() {
        pause = true;
    }

    public final void clear() {
        dialogQueue.clear();
    }

}
