package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class KustomizeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KustomizeDemoApplication.class, args);
    }

}
