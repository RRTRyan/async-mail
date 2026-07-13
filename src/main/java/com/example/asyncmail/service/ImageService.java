package com.example.asyncmail.service;

import com.example.asyncmail.dto.ImageRequest;
import com.example.asyncmail.endpoint.event.EventProducer;
import com.example.asyncmail.endpoint.event.model.ImageUploadEmail;
import com.example.asyncmail.file.bucket.BucketComponent;
import com.example.asyncmail.repository.ImageRepository;
import com.example.asyncmail.repository.model.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final EventProducer<ImageUploadEmail> eventProducer;
  private final ImageRepository imageRepository;
  private final BucketComponent bucketComponent;

  public List<Image> findAll() {
    return imageRepository.findAll();
  }

  public void save(ImageRequest request) {
    imageRepository.save(
        Image.builder()
            .fileName(request.getFilename())
            .email(request.getEmail())
            .createdAt(Instant.now())
            .build());

    eventProducer.accept(
        List.of(
            ImageUploadEmail.builder()
                .to(request.getEmail())
                .imageFileURI(uploadMonoImage(request.getImage()))
                .build()));
  }

  @SneakyThrows
  private String uploadMonoImage(MultipartFile image) {
    File file = createImageFile(image);
    bucketComponent.upload(convertToMonochrome(file), file.getName());
    return bucketComponent.presign(file.getName(), Duration.ofDays(1)).toString();
  }

  @SneakyThrows
  private File createImageFile(MultipartFile image) {
    String fileName = image.getOriginalFilename();
    File file =
        File.createTempFile(
            fileName.substring(0, fileName.lastIndexOf('.')), getFileExtension(fileName));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    outputStream.write(image.getBytes());
    outputStream.close();

    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(outputStream.toByteArray());
    fileOutputStream.close();

    return file;
  }

  @SneakyThrows
  private File convertToMonochrome(File image) {
    BufferedImage bufferedColorImage = ImageIO.read(image);
    if (bufferedColorImage == null) {
      return null;
    }
    BufferedImage bufferedMonoImage =
        new BufferedImage(
            bufferedColorImage.getWidth(),
            bufferedColorImage.getHeight(),
            BufferedImage.TYPE_BYTE_GRAY);

    String fileName = image.getName();
    File file =
        File.createTempFile(
            fileName.substring(0, fileName.lastIndexOf('.')), getFileExtension(fileName));

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bufferedMonoImage.createGraphics().drawImage(bufferedColorImage, 0, 0, null);
    ImageIO.write(bufferedMonoImage, getFileExtension(file.getName()), outputStream);
    outputStream.close();

    FileOutputStream fos = new FileOutputStream(file);
    fos.write(outputStream.toByteArray());
    fos.close();
    return file;
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) return null;
    return fileName.substring(fileName.lastIndexOf('.') + 1);
  }
}
