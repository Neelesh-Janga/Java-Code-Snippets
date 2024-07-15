package dev.neelesh.multithreading.lockbased.producer_consumer_problem;

import java.util.Random;

public class Producer implements Runnable {

    private final SharedResource resource;
    private final Random numberGenerator;
    private final String LOCK_TYPE;
    
    public Producer(SharedResource resource, Random numberGenerator, String lockType) {
        this.resource = resource;
        this.numberGenerator = numberGenerator;
        this.LOCK_TYPE = lockType;
    }

    @Override
    public void run() {
        while (true) {
            resource.produceData(numberGenerator.nextInt(1, 100));
            try {
                Thread.sleep(
                        LOCK_TYPE.toLowerCase().equals("monitorlock")
                                ? 2000 : (int) (Math.random() * 2000)
                );            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
