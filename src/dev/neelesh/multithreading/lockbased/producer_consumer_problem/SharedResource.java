package dev.neelesh.multithreading.lockbased.producer_consumer_problem;

public interface SharedResource {
    
    void consumeData();
    void produceData(int data);
}
