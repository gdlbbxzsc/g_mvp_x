package c.g.a.x.lib_support.views.splistener.custom;

/**
 * Created by Administrator on 2017/9/18.
 */


public abstract class BaseSPListener {

    private static final int MIN_CLICK_DELAY_TIME = 350;

    private volatile long lastClickTime = 0;

    boolean canClick() {
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastClickTime;
        lastClickTime = currentTime;
        return time > MIN_CLICK_DELAY_TIME;
    }
}
