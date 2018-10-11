package com.enmichuk.core.concurrency.producerconsumer;

import java.util.List;

class Consumer implements Runnable {
    private final List<Integer> taskQueue;
    private String name;

    public Consumer(List<Integer> sharedQueue, String name) {
        this.taskQueue = sharedQueue;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Consumer is started. Consumer name is " + name);
        while (true) {
            try {
                consume();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        System.out.println("Ready to consume. Consumer name is " + name);
        synchronized (taskQueue) {
            System.out.println("Start consuming. Consumer name is " + name);
            while (taskQueue.isEmpty()) {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            int i = taskQueue.remove(0);
            System.out.println("Consumed: " + i + ". Consumer name is " + name);
            taskQueue.notifyAll();
        }
    }
}