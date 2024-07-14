package dev.neelesh.multithreading.producer_consumer_problem.reentrantlock;

import dev.neelesh.multithreading.producer_consumer_problem.*;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedResourceImpl implements SharedResource {

    private Queue<Integer> queue;
    private final int MAX_SIZE = 5;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public SharedResourceImpl(Queue<Integer> queue) {
        this.queue = queue;
    }

    public void produceData(int data){
        lock.lock();
        try {
            while (queue.size() == MAX_SIZE)
                condition.await();
            queue.add(data);
            
            if(data % 5 == 0)
                modFive(); // Reentrancy example
            
            System.out.println("ReentrantLock :: " + Thread.currentThread().getName() + " produced : " + data);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void consumeData() {
        lock.lock();
        try {
            while(queue.isEmpty())
                condition.await();
            System.out.println("ReentrantLock :: " + Thread.currentThread().getName() + " consumed : " + queue.poll());
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    
    public void modFive(){
        lock.lock();
        try {
            System.out.println("Mod 5 value will be inserted by " + Thread.currentThread().getName() + " 👇🏼");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
