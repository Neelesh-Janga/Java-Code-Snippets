package dev.neelesh.multithreading.workstealingpool;

import java.util.concurrent.*;

public class WorkStealingPoolPractical {
    
    public static void main(String[] args) {
    
//        Parallelism levels are selected based on Available Processors
        ForkJoinPool forkJoinPool1 = (ForkJoinPool) Executors.newWorkStealingPool();

//        Parallelism levels are selected explicitly
//        ForkJoinPool forkJoinPool2 = (ForkJoinPool) Executors.newWorkStealingPool(2);

//        Parallelism levels are selected as Max(1, Available Processors - 1)
        ForkJoinPool forkJoinPool3 = ForkJoinPool.commonPool();
        
        int [] arr1 = {1,2,3,4,5,1,2,3,4,5};
        int [] arr2 = {1,2,3,4,5,6,7,8,9,10};
        
        System.out.println("----RecursiveTask Example:----");
        recursiveTaskPractical(forkJoinPool1, arr1);

        System.out.println("\n----RecursiveAction Example:----");
        recursiveActionPractical(forkJoinPool3, arr2);
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
