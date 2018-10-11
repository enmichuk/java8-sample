package com.enmichuk.core.concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Map<String, String> map = new HashMap<>();
        final ReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock();
            System.out.println("Write lock is acquired");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                map.put("foo", "bar");
            } finally {
                lock.writeLock().unlock();
                System.out.println("Write lock is released");
            }
        });

        Runnable readTask = () -> {
            System.out.println("Start to acquire read lock");
            lock.readLock().lock();
            System.out.println("Read lock is acquired");
            try {
                System.out.println(map.get("foo"));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        executor.shutdown();
    }
}
