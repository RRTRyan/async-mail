package com.example.asyncmail.endpoint.rest.controller;

import com.example.asyncmail.dto.ImageRequest;
import com.example.asyncmail.service.ImageService;
import com.example.asyncmail.validator.ImageRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageService imageService;
  private final ImageRequestValidator imageRequestValidator;

  @PostMapping
  public ResponseEntity<?> save(@RequestBody ImageRequest imageRequest) {
    imageRequestValidator.validate(imageRequest);
    imageService.save(imageRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return ResponseEntity.status(HttpStatus.OK).body(imageService.findAll());
  }
}
