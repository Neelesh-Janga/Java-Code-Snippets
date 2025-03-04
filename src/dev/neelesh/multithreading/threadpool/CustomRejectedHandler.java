package dev.neelesh.multithreading.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomRejectedHandler implements RejectedExecutionHandler {
    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected by " + Thread.currentThread().getName());
    }
}
