package dev.neelesh.multithreading.threadpool;

import java.util.concurrent.*;

public class ThreadPoolPractical {
    
    public static void main(String[] args) {
        ThreadPoolPractical practical = new ThreadPoolPractical();
        ThreadPoolExecutor executor = practical.new ThreadPoolExecutorPractical().getThreadPoolExecutor();
        executor.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 10; i++) {
            executor.submit(
                    () -> {
                        System.out.println("Task processed by: " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        
        executor.shutdown();
    }
    
    class CustomThreadFactory implements ThreadFactory {
        
        private static int threadCount = 0;
        
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Thread-"+(++threadCount));
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    class CustomRejectedHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task rejected by " + Thread.currentThread().getName());
        }
    }
    
    class ThreadPoolExecutorPractical{
        ThreadPoolExecutor executor;
        
        public ThreadPoolExecutorPractical() {
            executor = new ThreadPoolExecutor(
                    2, 4, 3, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<Runnable>(3),
                    new CustomThreadFactory(),
                    new CustomRejectedHandler()
            );
        }
        
        public ThreadPoolExecutor getThreadPoolExecutor() {
            return executor;
        }
        
    }
}
