package com.example.asyncmail.endpoint.event.model;

import java.time.Duration;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationEmail extends PojaEvent {
  private String to;
  private String registrationFileURI;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(10);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofSeconds(30);
  }
}
