package c.g.a.x.lib_guide;

import java.util.LinkedList;
import java.util.List;

public final class GuideControllerQueueHelper<T extends GuideController> {

    private final LinkedList<T> queue = new LinkedList<>();

    private volatile T currentGCler = null;//当前显示的Dialog

    private volatile boolean pause;

    public static GuideControllerQueueHelper getInstance() {
        return InnerInstance.INSTANCE;
    }

    private static class InnerInstance {
        private static final GuideControllerQueueHelper INSTANCE = new GuideControllerQueueHelper();
    }

    private GuideControllerQueueHelper() {
    }

    public final void addControllers(List<T> controllers) {
        for (T controller : controllers) {
            if (controller == null) continue;
            queue.offer(controller);
        }
    }

    public final void addController(T controller) {
        if (controller != null) queue.offer(controller);
    }

    public final void addControllerFirst(T controller) {
        if (controller != null) queue.offerFirst(controller);
    }

    public final void show() {

        if (pause) {
            pause = false;
            return;
        }

        if (currentGCler != null) return;

        //从队列中拿出一个Dialog实例,并从列表中移除
        currentGCler = queue.poll();

        //当队列为空的时候拿出来的会是null
        if (currentGCler == null) return;

        currentGCler.create();
        currentGCler.setOnDismissListener(dialog -> {
            //这边设置了dismiss监听,在监听回调中再次调用show方法,可以获取下一个弹窗
            currentGCler = null;
            show();
        });
    }

    public final void pause() {
        pause = true;
    }

    public final void clear() {
        queue.clear();
    }

}
