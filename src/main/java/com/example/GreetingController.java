package com.example;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final ApplicationEventPublisher publisher;
    private final AppConfig appConfig;
    private final DBConfig dbConfig;

    public GreetingController(ApplicationEventPublisher publisher, AppConfig config, DBConfig dbConfig) {
        this.publisher = publisher;
        this.appConfig = config;
        this.dbConfig = dbConfig;
    }

    @GetMapping("/greet")
    public String greet() {
        return appConfig.getMessage();
    }

    @GetMapping("db-credentials")
    public String credentials() {
        return dbConfig.getUsername() + ":" + dbConfig.getPassword();
    }

    @GetMapping("/down")
    public void down() {
        AvailabilityChangeEvent.publish(publisher, this, ReadinessState.REFUSING_TRAFFIC);
    }

    @GetMapping("/up")
    public void up() {
        AvailabilityChangeEvent.publish(publisher, this, ReadinessState.ACCEPTING_TRAFFIC);
    }
}
