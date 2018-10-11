package com.enmichuk.core.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlphonseAndGaston {

    public static final Object object = new Object();
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        executor.submit(new BowLoop(alphonse, gaston));
        executor.submit(new BowLoop(gaston, alphonse));
        executor.shutdown();
    }
}

class Friend {
    private final String name;
    private final Lock lock = new ReentrantLock();

    Friend(String name) {
        this.name = name;
    }

    private String getName() {
        return this.name;
    }

    private boolean impendingBow(Friend bower) {
        Boolean myLock = false;
        Boolean yourLock = false;
        try {
            myLock = lock.tryLock();
            yourLock = bower.lock.tryLock();
        } finally {
            if (! (myLock && yourLock)) {
                if (myLock) {
                    lock.unlock();
                }
                if (yourLock) {
                    bower.lock.unlock();
                }
            }
        }
        return myLock && yourLock;
    }

    public void bow(Friend bower) {
        if (impendingBow(bower)) {
            try {
                System.out.format("%s: %s has"
                                + " bowed to me!%n",
                        this.name, bower.getName());
                bower.bowBack(this);
            } finally {
                lock.unlock();
                bower.lock.unlock();
            }
        } else {
            System.out.format("%s: %s started to bow to me, but saw that I was already bowing to him.%n",
                    this.name, bower.getName());
        }
    }

    private void bowBack(Friend bower) {
        System.out.format("%s: %s has bowed back to me!%n",
                this.name, bower.getName());
    }
}

class BowLoop implements Runnable {
    private Friend bower;
    private Friend bowee;

    BowLoop(Friend bower, Friend bowee) {
        this.bower = bower;
        this.bowee = bowee;
    }

    public void run() {
        for (;;) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
            } catch (InterruptedException e) {}
            bowee.bow(bower);
        }
    }
}
