package dev.neelesh.multithreading.lockbased.producer_consumer_problem.monitorlock;

import dev.neelesh.multithreading.lockbased.producer_consumer_problem.*;
import java.util.*;

public class SharedResourceImpl implements SharedResource {

    private final Queue<Integer> queue;
    private final int MAX_SIZE = 5;

    public SharedResourceImpl(Queue<Integer> queue) {
        this.queue = queue;
    }

    public synchronized void consumeData(){
        while(queue.isEmpty()){
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("MonitorLock :: Consumed Data : " + queue.poll());
        notify();
    }

    public synchronized void produceData(int data){
        while(queue.size() == MAX_SIZE){
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("MonitorLock :: Produced Data : " + data);
        queue.add(data);
        notify();
    }
}
