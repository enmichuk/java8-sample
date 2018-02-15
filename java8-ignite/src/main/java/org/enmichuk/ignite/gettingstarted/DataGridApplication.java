package org.enmichuk.ignite.gettingstarted;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;

import java.util.concurrent.locks.Lock;

public class DataGridApplication {
    public static void main(String[] args) throws Exception {
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName("myCacheName");
        cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        try (Ignite ignite = Ignition.start()) {
            IgniteCache<String, Integer> cache = ignite.getOrCreateCache(cfg);

            // Store keys in cache (values will end up on different cache nodes).
            for (int i = 0; i < 10; i++)
                cache.put(Integer.toString(i), i);

            for (int i = 0; i < 10; i++)
                System.out.println("Got [key=" + i + ", val=" + cache.get(Integer.toString(i)) + ']');

            // Put-if-absent which returns previous value.
            Integer oldVal = cache.getAndPutIfAbsent("Hello", 11);
            System.out.println(oldVal);

            // Put-if-absent which returns boolean success flag.
            boolean success = cache.putIfAbsent("World", 22);
            System.out.println(success);

            // Replace-if-exists operation (opposite of getAndPutIfAbsent), returns previous value.
            oldVal = cache.getAndReplace("Hello", 12);
            System.out.println(oldVal);

            // Replace-if-exists operation (opposite of putIfAbsent), returns boolean success flag.
            success = cache.replace("World", 22);
            System.out.println(success);

            // Replace-if-matches operation.
            success = cache.replace("World", 2, 22);
            System.out.println(success);

            // Remove-if-matches operation.
            success = cache.remove("Hello", 1);
            System.out.println(success);

            try (Transaction tx = ignite.transactions().txStart()) {
                Integer hello = cache.get("Hello");

                if (hello == 1)
                    cache.put("Hello", 15);

                cache.put("World", 25);

                tx.commit();
            }
            oldVal = cache.get("Hello");
            System.out.println(oldVal);
            oldVal = cache.get("World");
            System.out.println(oldVal);

            // Lock cache key "Hello".
            Lock lock = cache.lock("Hello");
            lock.lock();
            try {
                cache.put("Hello", 11);
                cache.put("World", 22);
            } finally {
                lock.unlock();
            }
            oldVal = cache.get("Hello");
            System.out.println(oldVal);
            oldVal = cache.get("World");
            System.out.println(oldVal);
        }
    }
}
