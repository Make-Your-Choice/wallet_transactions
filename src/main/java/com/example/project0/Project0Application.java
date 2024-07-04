package com.example.project0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.project0.*"})
public class Project0Application {

    public static void main(String[] args) {
        SpringApplication.run(Project0Application.class, args);
    }

}
