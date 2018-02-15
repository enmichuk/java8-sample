package org.enmichuk.ignite.gettingstarted;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class LoggingApplication {
    public static void main(String[] args) {
        IgniteConfiguration cfg = new IgniteConfiguration();

        IgniteLogger log = new Slf4jLogger();

        cfg.setGridLogger(log);

        // Start Ignite node.
        try(Ignite ignite = Ignition.start(cfg)) {
            ignite.log().info("Info Message Logged!");
        }
    }
}
