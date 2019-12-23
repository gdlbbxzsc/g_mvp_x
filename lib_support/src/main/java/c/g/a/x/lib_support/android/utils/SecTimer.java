package c.g.a.x.lib_support.android.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by Administrator on 2018/4/9.
 */

public abstract class SecTimer {

    private long countdownInterval = 1000;

    private long startTime;

    private long passTime;

    private boolean pause = false;
    private boolean stop = false;

    public synchronized final void start() {
        if (stop) startTime = SystemClock.elapsedRealtime();
        pause = false;
        stop = false;
        handler.sendEmptyMessage(0);
    }

    public synchronized final void pause() {
        pause = true;
        handler.removeMessages(0);
    }

    public synchronized final void stop() {
        pause = true;
        stop = true;
        handler.removeMessages(0);
    }

    public abstract void onTick(long passTime) throws Exception;

    // handles counting down
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (SecTimer.this) {

                if (pause || stop) return;

                long now = SystemClock.elapsedRealtime();

                passTime = now - startTime;

                try {
                    onTick(passTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // take into account user's onTick taking time to execute
                long delay = countdownInterval - SystemClock.elapsedRealtime() + now;

                // special case: user's onTick took more than interval to
                // complete, skip to next interval
                while (delay < 0) delay += countdownInterval;

                sendEmptyMessageDelayed(0, delay);
            }
        }
    };
}
