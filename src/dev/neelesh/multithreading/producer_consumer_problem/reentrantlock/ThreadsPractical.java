package dev.neelesh.multithreading.producer_consumer_problem.reentrantlock;


import dev.neelesh.multithreading.producer_consumer_problem.Consumer;
import dev.neelesh.multithreading.producer_consumer_problem.Producer;
import dev.neelesh.multithreading.producer_consumer_problem.SharedResource;

import java.util.ArrayDeque;
import java.util.Random;

public class ThreadsPractical {

    public static void main(String[] args) {
        reentrantLockPractical();
    }
    
    private static void reentrantLockPractical(){
        SharedResource resource = new SharedResourceImpl(new ArrayDeque<>());
        Random random = new Random();

        Thread producer_1 = new Thread(new Producer(resource, random, "ReentrantLock"), "Producer 1");
        Thread consumer_1 = new Thread(new Consumer(resource, "ReentrantLock"), "Consumer 1");

        Thread producer_2 = new Thread(new Producer(resource, random, "ReentrantLock"), "Producer 2");
        Thread consumer_2 = new Thread(new Consumer(resource, "ReentrantLock"), "Consumer 2");

        producer_1.start();
        producer_2.start();
        consumer_1.start();
        consumer_2.start();
    }
}
