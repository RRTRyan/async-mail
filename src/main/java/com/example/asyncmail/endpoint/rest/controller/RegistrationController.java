package com.example.asyncmail.endpoint.rest.controller;

import com.example.asyncmail.service.RegistrationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
  private final RegistrationService registrationService;

  @PostMapping("/{courseId}/register")
  public ResponseEntity<?> register(@PathVariable UUID courseId, @RequestBody UUID userId) {
    registrationService.register(courseId, userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
