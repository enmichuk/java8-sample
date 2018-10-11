package com.enmichuk.core.concurrency.producerconsumer;

import java.util.List;

class Producer implements Runnable {
    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;

    public Producer(List<Integer> sharedQueue, int size) {
        this.taskQueue = sharedQueue;
        this.MAX_CAPACITY = size;
    }

    @Override
    public void run() {
        System.out.println("Producer is started");
        int counter = 0;
        while (true) {
            try {
                produce(counter++);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    private void produce(int i) throws InterruptedException {
        System.out.println("Ready to produce");
        synchronized (taskQueue) {
            System.out.println("Start producing");
            while (taskQueue.size() == MAX_CAPACITY) {
                System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
                taskQueue.wait();
            }

            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("Produced: " + i);
            taskQueue.notifyAll();
        }
    }
}
