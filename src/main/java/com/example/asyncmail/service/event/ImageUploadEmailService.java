package com.example.asyncmail.service.event;

import com.example.asyncmail.endpoint.event.model.ImageUploadEmail;
import com.example.asyncmail.mail.Email;
import com.example.asyncmail.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageUploadEmailService implements Consumer<ImageUploadEmail> {
  private final Mailer mailer;

  @Override
  @SneakyThrows
  public void accept(ImageUploadEmail imageUploadEmail) {
    InternetAddress emailAddress = new InternetAddress(imageUploadEmail.getTo());
    mailer.accept(
        new Email(
            new InternetAddress(emailAddress.getAddress()),
            List.of(),
            List.of(),
            "Image upload",
            "<div><p>Your image has been saved successfully</p><a href=\""
                + imageUploadEmail.getImageFileURI()
                + "\">Take a look at it with the link</a></div>",
            List.of()));
  }

  @Override
  public Consumer<ImageUploadEmail> andThen(Consumer<? super ImageUploadEmail> after) {
    return Consumer.super.andThen(after);
  }
}
