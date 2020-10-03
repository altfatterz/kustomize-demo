package com.example;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final ApplicationEventPublisher publisher;
    private final AppConfig config;

    public GreetingController(ApplicationEventPublisher publisher, AppConfig config) {
        this.publisher = publisher;
        this.config = config;
    }

    @GetMapping("/greet")
    public String greet() {
        return config.getMessage();
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
