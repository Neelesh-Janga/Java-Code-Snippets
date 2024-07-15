package dev.neelesh.multithreading.lockbased.producer_consumer_problem.readwritelock;

import dev.neelesh.multithreading.lockbased.producer_consumer_problem.SharedResource;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedResourceImpl implements SharedResource {
    
    private Queue<Integer> queue;
    private final int MAX_SIZE = 5;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
//    private Condition readLockCondition = lock.readLock().newCondition(); // This is not possible
    private Condition writeLockCondition = lock.writeLock().newCondition();
    
    public SharedResourceImpl(Queue<Integer> queue) {
        this.queue = queue;
    }
    
    public void produceData(int data){
        lock.writeLock().lock();
        try {
            while (queue.size() == MAX_SIZE)
                writeLockCondition.await();
            queue.add(data);
            
            if(data % 10 == 0) modTen(); // Reentrancy example
            
            System.out.println("ReadWriteLock :: " + Thread.currentThread().getName() + " produced : " + data);
            writeLockCondition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }
    
    public void consumeData() {
        lock.writeLock().lock();
        try {
            while(queue.isEmpty()) writeLockCondition.await();
            System.out.println("ReadWriteLock :: " + Thread.currentThread().getName() + " consumed : " + queue.poll());
            readAllContents();
            writeLockCondition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }
    
    public void modTen(){
        lock.writeLock().lock();
        try {
            System.out.println("Mod 10 value will be inserted by " + Thread.currentThread().getName() + " 👇🏼");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }
    
    public void readAllContents(){
        lock.readLock().lock();
        try {
            System.out.printf(
                    "%s printing the Queue: %s\n",
                    Thread.currentThread().getName(), queue
            );
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
}
