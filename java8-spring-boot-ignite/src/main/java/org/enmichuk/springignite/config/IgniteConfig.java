package org.enmichuk.springignite.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.enmichuk.springignite.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableIgniteRepositories("org.enmichuk.springignite.dao.repository")
public class IgniteConfig {
    @Bean
    IgniteConfiguration igniteConfiguration() {

        IgniteConfiguration config = new IgniteConfiguration();

        // Setting some custom name for the node.
        config.setIgniteInstanceName("springDataNode");

        // Enabling peer-class loading feature.
        config.setPeerClassLoadingEnabled(true);

        IgniteLogger log = new Slf4jLogger();
        config.setGridLogger(log);

        // Defining and creating a new cache to be used by Ignite Spring Data repository.
        CacheConfiguration cacheConfig = new CacheConfiguration("PersonCache");

        // Setting SQL schema for the cache.
        cacheConfig.setIndexedTypes(Long.class, Person.class);

        config.setCacheConfiguration(cacheConfig);

        return config;
    }

    @Bean(destroyMethod = "close")
    public Ignite igniteInstance(IgniteConfiguration igniteConfiguration) {
        return Ignition.start(igniteConfiguration);
    }
}
