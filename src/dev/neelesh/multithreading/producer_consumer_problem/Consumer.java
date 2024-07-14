package dev.neelesh.multithreading.producer_consumer_problem;

public class Consumer implements Runnable {

    private final SharedResource resource;
    private final String LOCK_TYPE;

    public Consumer(SharedResource resource, String lockType) {
        this.resource = resource;
        this.LOCK_TYPE = lockType;
    }

    @Override
    public void run() {
        while (true) {
            resource.consumeData();
            try {
                Thread.sleep(
                        LOCK_TYPE.toLowerCase().equals("monitorlock")
                                ? 5000 : (int) (Math.random() * 2000)
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
