package org.enmichuk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecuredSpringApp {
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(SecuredSpringApp.class, args);
    }
}
