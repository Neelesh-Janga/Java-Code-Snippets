package dev.neelesh.multithreading.producer_consumer_problem.monitorlock;


import dev.neelesh.multithreading.producer_consumer_problem.Consumer;
import dev.neelesh.multithreading.producer_consumer_problem.Producer;

import java.util.ArrayDeque;
import java.util.Random;

public class ThreadsPractical {

    public static void main(String[] args) {
        monitorLockPractical();
    }

    private static void monitorLockPractical(){
        SharedResourceImpl resource = new SharedResourceImpl(new ArrayDeque<>());

        Thread producerThread = new Thread(new Producer(resource, new Random(), "MonitorLock"));
        Thread consumerThread = new Thread(new Consumer(resource, "MonitorLock"));

        producerThread.start();
        consumerThread.start();

        try{
            producerThread.join();
            consumerThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
