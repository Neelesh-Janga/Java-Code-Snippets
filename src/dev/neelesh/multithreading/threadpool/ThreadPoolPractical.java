package dev.neelesh.multithreading.threadpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        
        
        // If you don't have access to list variable then this is how you can get the list.
        Future<?> futureObject;
        {
            final List<Integer> list = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
            futureObject = executor.submit(() -> {}, list);
        }
        
        try {
            System.out.println("Object obtained using Future.get():");
            Object o = futureObject.get();
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }
}
