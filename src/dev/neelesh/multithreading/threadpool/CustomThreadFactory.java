package dev.neelesh.multithreading.threadpool;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
    
    private static int threadCount = 0;
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "Thread-" + (++threadCount));
        t.setDaemon(false);
        t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
