package com.enmichuk.core.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferSynchronyzedWithLock {
    private static final BoundedBuffer buffer = new BoundedBuffer();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            for (; ; ) {
                try {
                    Long taken = buffer.take();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.submit(() -> {
            for (; ; ) {
                try {
                    Long time = System.currentTimeMillis();
                    buffer.put(time);
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
    }
}

class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition isFull = lock.newCondition();
    private final Condition isEmpty = lock.newCondition();

    private final Long[] items = new Long[10];
    private int putptr, takeptr, count;

    void put(Long x) throws InterruptedException {
        System.out.println("Put start");
        lock.lock();
        try {
            while (count == items.length) {
                System.out.println("Is full");
                isFull.await();
            }
            items[putptr] = x;
            System.out.println("Put " + x + " on " + putptr + " position");
            if (++putptr == items.length) putptr = 0;
            ++count;
            isEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    Long take() throws InterruptedException {
        System.out.println("Take start");
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("Is empty");
                isEmpty.await();
            }
            Long x = items[takeptr];
            System.out.println("Take " + x + " on " + takeptr + " position");
            if (++takeptr == items.length) takeptr = 0;
            --count;
            isFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
