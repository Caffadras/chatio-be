package com.example.chatio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ChatioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatioApplication.class, args);
    }

}
