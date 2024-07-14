package dev.neelesh.multithreading.producer_consumer_problem;

public interface SharedResource {
    
    void consumeData();
    void produceData(int data);
}
