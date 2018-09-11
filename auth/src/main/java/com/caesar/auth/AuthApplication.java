package com.caesar.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.caesar.commons", "com.caesar.auth"})
public class AuthApplication {

    public static void main(String... args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
