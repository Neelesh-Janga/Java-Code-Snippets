package dev.neelesh.multithreading.workstealingpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class WorkStealingPoolPractical {
    
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        
        int arr[] = {1,2,3,4,5,6,7,8,9,10};
        
        System.out.println("----RecursiveTask Example:----");
        recursiveTaskPractical(forkJoinPool, arr);

        System.out.println("\n----RecursiveAction Example:----");
        recursiveActionPractical(forkJoinPool, arr);
        
        forkJoinPool.shutdown();
    }
    
    static void recursiveTaskPractical(ForkJoinPool forkJoinPool, int [] arr){
        Future<Integer> future = forkJoinPool.submit(new Task(0, arr.length-1, arr));
        
        try {
            System.out.println("Sum of all numbers in the array returned from compute(): " + future.get());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void recursiveActionPractical(ForkJoinPool forkJoinPool, int [] arr){
        Future<Void> future = forkJoinPool.submit(new OtherTask(0, arr.length-1, arr));
        
        try {
            future.get();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Task extends RecursiveTask<Integer> {
    
    private int start, mid, end;
    private int[] arr;
    int sum = 0;
    
    public Task(int start, int end, int[] arr) {
        this.start = start;
        this.mid = start + (end - start) / 2;
        this.end = end;
        this.arr = arr;
    }
    
    @Override
    protected Integer compute() {
        System.out.println("Compute() by Thread: " + Thread.currentThread().getName());
        if(end - start <= 2){
            for(int i = start; i <= end; i++) sum += arr[i];
            return sum;
        }else{
            Task task1 = new Task(start, mid, arr);
            Task task2 = new Task(mid+1, end, arr);
            
            task1.fork();
            task2.fork();
            
            return task1.join() + task2.join();
        }
    }
}

class OtherTask extends RecursiveAction {
    
    private int start, mid, end;
    private int[] arr;
    private int sum = 0;
    
    public OtherTask(int start, int end, int[] arr) {
        this.start = start;
        this.mid = start + (end - start) / 2;
        this.end = end;
        this.arr = arr;
    }
    
    @Override
    protected void compute() {
        System.out.println("Compute() by Thread: " + Thread.currentThread().getName());
        if(end - start <= 2){
            for(int i = start; i <= end; i++) sum += arr[i];
        }else{
            Task task1 = new Task(start, mid, arr);
            Task task2 = new Task(mid+1, end, arr);
            
            task1.fork();
            task2.fork();
            
            sum = task1.join() + task2.join();
            
            System.out.println("Sum of all numbers in the array: " + sum);
        }
    }
}
