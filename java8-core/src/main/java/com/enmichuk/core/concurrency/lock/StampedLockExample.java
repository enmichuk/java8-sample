package com.enmichuk.core.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    public static final Data data = new Data();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            data.set(100);
        });
        executor.submit(() -> {
            System.out.println(data.get());
        });
        executor.submit(() -> {
            System.out.println(data.getOptimistic());
        });
        executor.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.set(150);
        });
        executor.submit(() -> {
            System.out.println(data.getOptimistic());
        });
        executor.shutdown();
    }
}

class Data {
    public final StampedLock lock = new StampedLock();
    private int data;

    public void set(int data) {
        long stamp = lock.writeLock();
        try {
            this.data = data;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int get() {
        long stamp = lock.readLock();
        try {
            return data;
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public int getOptimistic() {
        long stamp = lock.tryOptimisticRead();
        int currentData = data;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("lock.validate(stamp) = " + lock.validate(stamp));
        return currentData;
    }

}