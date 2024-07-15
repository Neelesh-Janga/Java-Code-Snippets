package dev.neelesh.multithreading.lockfree;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerPractical {
    
    public static void main(String[] args) {
        
        Version version = new Version(new AtomicInteger(0));
        
        Runnable runnable = () -> {
            int limit = 5;
            while (limit-- > 0) {
                if (Math.round(Math.random() * 10) < 5) {
                    version.incrementAtomicInteger();
                } else {
                    version.incrementAtomicInteger(10);
                }
                if (limit != 0) {
                    try{
                        Thread.sleep(4000);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        
        Thread t1 = new Thread(runnable, "Thread-1");
        
        Thread t2 = new Thread(runnable, "Thread-2");
        
        t1.start();
        t2.start();
        
        try{
            t1.join();
            t2.join();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class Version{
        private final AtomicInteger atomicInteger;
        
        public Version(AtomicInteger atomicInteger) {
            this.atomicInteger = atomicInteger;
        }
        
        public void incrementAtomicInteger(){
            System.out.println(
                    Thread.currentThread().getName()
                            + " incremented atomicInteger by 1: "
                            + this.atomicInteger.incrementAndGet()
            );
        }
        
        public void incrementAtomicInteger(int value){
            System.out.println(
                    Thread.currentThread().getName()
                            + " updated atomicInteger by "
                            + value
                            + ": "
                            + this.atomicInteger.addAndGet(value)
            );
        }
    }
}
