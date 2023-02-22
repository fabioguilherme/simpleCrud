package com.portofolio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleCrudBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCrudBatchApplication.class, args);
    }
}
