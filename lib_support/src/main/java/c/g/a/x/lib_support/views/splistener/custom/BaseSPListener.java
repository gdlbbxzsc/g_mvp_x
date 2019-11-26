package c.g.a.x.lib_support.views.splistener.custom;

/**
 * Created by Administrator on 2017/9/18.
 */


public abstract class BaseSPListener {

    private static final int MIN_CLICK_DELAY_TIME = 350;

    private long lastClickTime = 0;

    protected boolean canClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        }
        lastClickTime = currentTime;
        return true;
    }
}
