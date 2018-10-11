package com.enmichuk.core.concurrency.waitnotify;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable {
    private Data data;

    public Receiver(Data data) {
        this.data = data;
    }

    public void run() {
        for(String receivedMessage = data.receive(); !"End".equals(receivedMessage); receivedMessage = data.receive()) {
            System.out.println("Receiving message: " + receivedMessage);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }
    }
}