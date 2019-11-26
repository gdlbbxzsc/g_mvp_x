package c.g.a.x.applogic_api.thread;

import android.util.Log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2019/9/6.
 */

public final class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "ARouter task pool No." + poolNumber.getAndIncrement() + ", thread No.";
    }

    public Thread newThread(Runnable runnable) {
        String threadName = namePrefix + threadNumber.getAndIncrement();
        Log.d("DefaultThreadFactory=>", "Thread production, name is [" + threadName + "]");
        Thread thread = new Thread(group, runnable, threadName, 0);
        if (thread.isDaemon()) {   //设为非后台线程
            thread.setDaemon(false);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) { //优先级为normal
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        // 捕获多线程处理中的异常
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.d("DefaultThreadFactory=>", "Running task appeared exception! Thread [" + thread.getName() + "], because [" + ex.getMessage() + "]");
            }
        });
        return thread;
    }
}