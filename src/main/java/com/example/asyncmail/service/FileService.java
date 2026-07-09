package com.example.asyncmail.service;

import com.example.asyncmail.file.bucket.BucketComponent;
import com.example.asyncmail.repository.model.Course;
import com.example.asyncmail.repository.model.User;
import java.io.*;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@RequiredArgsConstructor
public class FileService {
  private final BucketComponent bucketComponent;

  @SneakyThrows
  public String uploadRegistrationPDF(User user, Course course) {
    File file = generateRegistrationPDF(populateRegistrationTemplate(user, course));
    bucketComponent.upload(file, file.getName());
    return bucketComponent.presign(file.getName(), Duration.ofDays(1)).toString();
  }

  private String populateRegistrationTemplate(User user, Course course) {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    Context context = new Context();
    context.setVariables(Map.of("userName", user.getFirstName(), "courseName", course.getName()));
    return templateEngine.process("RegistrationTemplate", context);
  }

  @SneakyThrows
  private File generateRegistrationPDF(String populatedHtml) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(populatedHtml);
    renderer.layout();
    renderer.createPDF(outputStream);

    outputStream.close();

    File file = File.createTempFile(UUID.randomUUID().toString().replace("-", ""), ".pdf");
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(outputStream.toByteArray());
    fileOutputStream.close();

    return file;
  }
}
