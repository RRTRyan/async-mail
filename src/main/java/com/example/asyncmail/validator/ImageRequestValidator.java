package com.example.asyncmail.validator;

import com.example.asyncmail.dto.ImageRequest;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ImageRequestValidator {
  public void validate(ImageRequest request) throws BadRequestException {
    if (request.getEmail() == null || request.getEmail().isEmpty()) {
      throw new BadRequestException("Email is required");
    }
    if (request.getImage() == null || request.getImage().isEmpty()) {
      throw new BadRequestException("Image is required");
    }
    if (request.getFilename() == null || request.getFilename().isEmpty()) {
      throw new BadRequestException("Filename is required");
    }
    List<String> supportedFileTypes = List.of("jpeg", "png", "jpg");
    if (!request.getFilename().contains(".")
        || !supportedFileTypes.contains(
            request
                .getFilename()
                .toLowerCase()
                .substring(request.getFilename().toLowerCase().lastIndexOf(".")))) {
      throw new BadRequestException("Unsupported file type");
    }
  }
}
