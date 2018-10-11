package com.enmichuk.core.concurrency.producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Integer> taskQueue = new ArrayList<Integer>();
        executor.execute(new Producer(taskQueue, 5));
        executor.execute(new Consumer(taskQueue, "Consumer"));
        System.out.println("End submitting of tasks");
        executor.shutdown();
    }
}
