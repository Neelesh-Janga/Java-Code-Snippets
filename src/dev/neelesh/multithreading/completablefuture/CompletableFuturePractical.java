package dev.neelesh.multithreading.completablefuture;

import dev.neelesh.multithreading.threadpool.CustomThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFuturePractical {
    
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 5, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1), new CustomThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        
        CompletableFuture<?> chain1 = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Chain-1: supplyAsync (" + Thread.currentThread().getName() + ")");
                    return "I";
                }, executor
        );
        
        CompletableFuture<?> chain2 = chain1.thenApplyAsync(
                (obj) -> {
                    System.out.println("Chain-2.0: thenApplyAsync (" + Thread.currentThread().getName() + ")");
                    return obj + " am";
                }, executor
        );
        
        chain2 = chain2.thenApply(
                (obj) -> {
                    System.out.println("Chain-2.1: thenApply (" + Thread.currentThread().getName() + ")");
                    return obj + " Neelesh";
                }
        );
        
        chain2 = chain2.thenApplyAsync(
                (obj) -> {
                    System.out.println("Chain-2.2: thenApplyAsync (" + Thread.currentThread().getName() + ")");
                    return obj + " Janga,";
                }, executor
        );
        
        CompletableFuture<?> chain3 = chain2.thenComposeAsync(
                (obj) -> {
                    System.out.println("Chain-3.0: thenComposeAsync (" + Thread.currentThread().getName() + ")");
                    return CompletableFuture.supplyAsync(() -> {
                        System.out.println("Chain-3.0.1: supplyAsync (" + Thread.currentThread().getName() + ")");
                        return obj + " demonstrating";
                    }, executor);
                }, executor
        );
        
        chain3 = chain3.thenCompose(
                (obj) -> {
                    System.out.println("Chain-3.1: thenCompose (" + Thread.currentThread().getName() + ")");
                    return CompletableFuture.supplyAsync(() -> {
                        System.out.println("Chain-3.1.1: supplyAsync (" + Thread.currentThread().getName() + ")");
                        return obj + " CompletableFuture";
                    }, executor);
                }
        );
        
        chain3 = chain3.thenComposeAsync(
                (obj) -> {
                    System.out.println("Chain-3.2: thenComposeAsync (" + Thread.currentThread().getName() + ")");
                    return CompletableFuture.supplyAsync(() -> {
                        System.out.println("Chain-3.2.1: supplyAsync (" + Thread.currentThread().getName() + ")");
                        return obj + " Interface in Java.";
                    }, executor);
                }, executor
        );
        
        chain3.thenAcceptAsync(
                (obj) -> {
                    System.out.println("Chain-4: thenAcceptAsync (" + Thread.currentThread().getName() + ")");
                    System.out.println("Chained data: " + obj +  " (" + Thread.currentThread().getName() + ")");
                }, executor
        );
        
        CompletableFuture<?> completableFuture = CompletableFuture.supplyAsync(
                () -> "This interface is awesome!!!", executor);

        var res = chain3.thenCombineAsync(completableFuture, (var obj, var newObj) -> obj + " " + newObj)
                .thenAcceptAsync((obj) -> {
                    System.out.println(
                            "Data after thenCombineAsync: " + obj +  " (" + Thread.currentThread().getName() + ")");
                }, executor);
        
        
        try {
//            Type-1: Recommended based on scenarios
//            chain1.get();
//            chain2.get();
//            chain3.get();
//            res.get();

//            Type-2: Recommended based on scenarios
            CompletableFuture<Void> checkAllAreDone = CompletableFuture.allOf(chain1, chain2, chain3, res);
            checkAllAreDone.get();
            
//            Type-3: Not recommended
//            while(!checkAllAreDone.isDone());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
