package org.enmichuk.ignite.gettingstarted;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryObject;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.resources.IgniteInstanceResource;

import javax.cache.Cache;

public class IgniteSqlComputeApplication {
    public static void main(String[] args) throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();

        IgniteLogger log = new Slf4jLogger();

        cfg.setGridLogger(log);
        try (Ignite ignite = Ignition.start(cfg)) {
            IgniteSqlApplication.createSchema();
            IgniteSqlApplication.insertRecords(ignite);
            sendMessages(ignite, 2);
        }
    }

    private static void sendMessages(Ignite ignite, long cityId) {
        // Sending the logic to a cluster node that stores Denver and its residents.
        ignite.compute().affinityRun(IgniteSqlApplication.SQL_PUBLIC_CITY, cityId, new IgniteRunnable() {

            @IgniteInstanceResource
            Ignite ignite;

            @Override
            public void run() {
                // Getting an access to Persons cache.
                IgniteCache<BinaryObject, BinaryObject> people = ignite.cache(IgniteSqlApplication.SQL_PUBLIC_PERSON).withKeepBinary();

                ScanQuery<BinaryObject, BinaryObject> query = new ScanQuery <>();

                try (QueryCursor<Cache.Entry<BinaryObject, BinaryObject>> cursor = people.query(query)) {
                    // Iteration over the local cluster node data using the scan query.
                    for (Cache.Entry<BinaryObject, BinaryObject> entry : cursor) {
                        BinaryObject personKey = entry.getKey();

                        // Picking Denver residents only only.
                        if (personKey.<Long>field("CITY_ID") == cityId) {
                            BinaryObject person = entry.getValue();

                            System.out.println("Sending message to " + person.toString());
                        }
                    }
                }
            }
        });
    }
}
