package org.enmichuk.springignite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IgniteSpringApp {
    //https://spring.io/guides/gs/accessing-data-jpa/
    //https://dzone.com/articles/spring-boot-integration-with-apache-ignite-and-its
    //https://apacheignite-mix.readme.io/v2.3/docs/spring-data
    //https://apacheignite-mix.readme.io/v2.3/docs/spring-caching
    public static void main(String[] args) {
        SpringApplication.run(IgniteSpringApp.class, args);
    }
}
