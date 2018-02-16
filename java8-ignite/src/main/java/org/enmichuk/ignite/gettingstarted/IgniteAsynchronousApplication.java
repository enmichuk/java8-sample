package org.enmichuk.ignite.gettingstarted;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class IgniteAsynchronousApplication {
    public static void main(String[] args) throws Exception {
        IgniteConfiguration cfg = new IgniteConfiguration();

        IgniteLogger log = new Slf4jLogger();

        cfg.setGridLogger(log);
        try (Ignite ignite = Ignition.start(cfg)) {
            IgniteCompute compute = ignite.compute();

            // Execute a closure asynchronously.
            IgniteFuture<String> fut = compute.callAsync(() -> "Hello World");

            // Listen for completion and print out the result.
            fut.listen(f -> System.out.println("Job result: " + f.get()));
        }
    }
}
