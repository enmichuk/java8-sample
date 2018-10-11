package com.enmichuk.core.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.TimeUnit;

public class UncaughtExceptionHandlerExample {

    final static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        executor.execute(new Task());
    }
}

class ExceptionHandler implements UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Stack Trace:");
        e.printStackTrace(System.out);
        UncaughtExceptionHandlerExample.executor.execute(new Task());
    }
}

class Task implements Runnable {
    @Override
    public void run() {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        throw new RuntimeException("Exception");
    }
}
