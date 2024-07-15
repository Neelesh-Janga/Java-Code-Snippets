package dev.neelesh.multithreading.lockbased.producer_consumer_problem.readwritelock;


import dev.neelesh.multithreading.lockbased.producer_consumer_problem.Consumer;
import dev.neelesh.multithreading.lockbased.producer_consumer_problem.Producer;
import dev.neelesh.multithreading.lockbased.producer_consumer_problem.SharedResource;

import java.util.ArrayDeque;
import java.util.Random;

public class ThreadsPractical {

    public static void main(String[] args) {
        readwriteLockPractical();
    }
    
    private static void readwriteLockPractical(){
        SharedResource resource = new SharedResourceImpl(new ArrayDeque<>());
        Random random = new Random();

        Thread producer_1 = new Thread(new Producer(resource, random, "ReadWriteLock"), "Producer 1");
        Thread consumer_1 = new Thread(new Consumer(resource, "ReadWriteLock"), "Consumer 1");

        Thread producer_2 = new Thread(new Producer(resource, random, "ReadWriteLock"), "Producer 2");
        Thread consumer_2 = new Thread(new Consumer(resource, "ReadWriteLock"), "Consumer 2");

        producer_1.start();
        producer_2.start();
        consumer_1.start();
        consumer_2.start();
    }
}
