package dev.neelesh.multithreading.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolPractical {
    
    public static void main(String[] args) {
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 4, 3, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(3),
                new CustomThreadFactory(), new CustomRejectedHandler()
        );
        
        executor.allowCoreThreadTimeOut(true);
        
        Map<Integer, Future<?>> futureMap = new HashMap<>();
        
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " thread calling executor.submit(..)");
            Future<?> future = executor.submit(
                    () -> {
                        System.out.println("Task processed by: " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            System.out.println("Queue size = " + executor.getQueue().size());
            futureMap.put(i+1, future);
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("List of futures for the task allocated threads:");
        futureMap.forEach((key, value) -> System.out.println(key + " : " + value.isDone()));
        
        executor.shutdown();
    }
    
    static class CustomThreadFactory implements ThreadFactory {
        
        private static int threadCount = 0;
        
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Thread-"+(++threadCount));
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    static class CustomRejectedHandler implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task rejected by " + Thread.currentThread().getName());
        }
    }
}
