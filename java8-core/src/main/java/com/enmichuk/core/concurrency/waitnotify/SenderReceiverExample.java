package com.enmichuk.core.concurrency.waitnotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SenderReceiverExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Data data = new Data();
        executor.submit(new Sender(data));
        executor.submit(new Receiver(data));
        executor.shutdown();
    }
}
