package com.smarthardwareshop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application.
 */
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class Application {

    /**
     * Application start method.
     *
     * @param args Application arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
