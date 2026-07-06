package com.example.asyncmail.endpoint.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationEmail extends PojaEvent {
    private String to;

    @Override
    public Duration maxConsumerDuration() {
        return Duration.ofSeconds(10);
    }

    @Override
    public Duration maxConsumerBackoffBetweenRetries() {
        return Duration.ofSeconds(30);
    }
}
