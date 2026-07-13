package com.example.asyncmail.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequest {
  private MultipartFile image;
  private String email;

  public String getFilename() {
    return (this.image != null) ? this.image.getOriginalFilename() : null;
  }
}
