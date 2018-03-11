package org.enmichuk.springignite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IgniteSpringApp {
    //https://apacheignite-mix.readme.io/v2.3/docs/spring-data
    //https://apacheignite-mix.readme.io/v2.3/docs/spring-caching
    public static void main(String[] args) {
        SpringApplication.run(IgniteSpringApp.class, args);
    }
}
