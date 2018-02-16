package org.enmichuk.ignite.gettingstarted.binary;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This example demonstrates use of binary objects with Ignite cache.
 * Specifically it shows that binary objects are simple Java POJOs and do not require any special treatment.
 * <p>
 * The example executes several put-get operations on Ignite cache with binary values. Note that
 * it demonstrates how binary object can be retrieved in fully-deserialized form or in binary object
 * format using special cache projection.
 * <p>
 * Remote nodes should always be started with the following command:
 * {@code 'ignite.{sh|bat} examples/config/example-ignite.xml'}
 * <p>
 * Alternatively you can run {@link org.enmichuk.ignite.gettingstarted.ExampleNodeStartup} in another JVM which will
 * start a node with {@code examples/config/example-ignite.xml} configuration.
 */
public class IgniteBinaryObjectApplication {
    public static final String CACHE_NAME = "my_cache";

    public static void main(String[] args) {
        try (Ignite ignite = Ignition.start("config/example-ignite.xml")) {
            System.out.println();
            System.out.println("Binary objects cache put-get example started.");

            CacheConfiguration<Long, Person> cfg = new CacheConfiguration<>();

            cfg.setCacheMode(CacheMode.PARTITIONED);
            cfg.setName(CACHE_NAME);
            cfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);

            try (IgniteCache<Long, Person> cache = ignite.getOrCreateCache(cfg)) {
                if (ignite.cluster().forDataNodes(cache.getName()).nodes().isEmpty()) {
                    System.out.println();
                    System.out.println("This example requires remote cache node nodes to be started.");
                    System.out.println("Please start at least 1 remote cache node.");
                    System.out.println("Refer to example's javadoc for details on configuration.");
                    System.out.println();

                    return;
                }

                putGet(cache);
                putGetBinary(cache);
                putGetAll(cache);
                putGetAllBinary(cache);

                System.out.println();
            } finally {
                ignite.destroyCache(CACHE_NAME);
            }
        }
    }

    /**
     * Execute individual put and get.
     *
     * @param cache Cache.
     */
    private static void putGet(IgniteCache<Long, Person> cache) {
        // Create new person binary object to store in cache.
        Person person = new Person("Evgeny", new Address("1096 Eddy Street, San Francisco, CA", 94109));

        // Put created data entry to cache.
        cache.put(person.getId(), person);

        // Get recently created person as a strongly-typed fully de-serialized instance.
        Person personFromCache = cache.get(person.getId());

        System.out.println();
        System.out.println("Retrieved person instance from cache: " + personFromCache);
    }

    /**
     * Execute individual put and get, getting value in binary format, without de-serializing it.
     *
     * @param cache Cache.
     */
    private static void putGetBinary(IgniteCache<Long, Person> cache) {
        Person person = new Person("Evgeny", new Address("1096 Eddy Street, San Francisco, CA", 94109));

        // Put created data entry to cache.
        cache.put(person.getId(), person);

        // Get cache that will get values as binary objects.
        IgniteCache<Long, BinaryObject> binaryCache = cache.withKeepBinary();

        // Get recently created person as a binary object.
        BinaryObject binaryPerson = binaryCache.get(person.getId());

        // Get person's name from binary object (note that object doesn't need to be fully deserialized).
        String name = binaryPerson.field("name");

        System.out.println();
        System.out.println("Retrieved person name from binary object: " + name);
    }

    /**
     * Execute bulk operations
     */
    private static void putGetAll(IgniteCache<Long, Person> cache) {
        Person person1 = new Person("Microsoft", new Address("1096 Eddy Street, San Francisco, CA", 94109));

        Person person2 = new Person("Red Cross", new Address("184 Fidler Drive, San Antonio, TX", 78205)); // Last update time.

        Map<Long, Person> map = new HashMap<>();

        map.put(person1.getId(), person1);
        map.put(person2.getId(), person2);

        // Put created data entries to cache.
        cache.putAll(map);

        // Get recently created persons as a strongly-typed fully de-serialized instances.
        Map<Long, Person> mapFromCache = cache.getAll(map.keySet());

        System.out.println();
        System.out.println("Retrieved person instances from cache:");

        for (Person person : mapFromCache.values())
            System.out.println(person);
    }

    /**
     * Execute bulk operations, getting values in binary format, without de-serializing it.
     */
    private static void putGetAllBinary(IgniteCache<Long, Person> cache) {
        Person person1 = new Person("Microsoft", new Address("1096 Eddy Street, San Francisco, CA", 94109));

        Person person2 = new Person("Red Cross", new Address("184 Fidler Drive, San Antonio, TX", 78205)); // Last update time.

        Map<Long, Person> map = new HashMap<>();

        map.put(person1.getId(), person1);
        map.put(person2.getId(), person2);

        // Put created data entries to cache.
        cache.putAll(map);

        // Get cache that will get values as binary objects.
        IgniteCache<Long, BinaryObject> binaryCache = cache.withKeepBinary();

        // Get recently created persons as binary objects.
        Map<Long, BinaryObject> poMap = binaryCache.getAll(map.keySet());

        Collection<String> names = new ArrayList<>();

        // Get persons' names from binary objects (note that objects don't need to be fully deserialized).
        for (BinaryObject po : poMap.values())
            names.add(po.field("name"));

        System.out.println();
        System.out.println("Retrieved person names from binary objects: " + names);
    }
}
