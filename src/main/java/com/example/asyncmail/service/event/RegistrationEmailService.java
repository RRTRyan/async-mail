package com.example.asyncmail.service.event;

import com.example.asyncmail.endpoint.event.model.RegistrationEmail;
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
public class RegistrationEmailService implements Consumer<RegistrationEmail> {
  private final Mailer mailer;

  @Override
  @SneakyThrows
  public void accept(RegistrationEmail registrationEmail) {
    InternetAddress emailAddress = new InternetAddress(registrationEmail.getTo());
    mailer.accept(
        new Email(
            new InternetAddress(emailAddress.getAddress()),
            List.of(),
            List.of(),
            "Course registration",
            "<div><p>Your registration to the course have been successfully saved</p><a href=\""
                + registrationEmail.getRegistrationFileURI()
                + "\">Get your ticket here</a></div>",
            List.of()));
  }

  @Override
  public Consumer<RegistrationEmail> andThen(Consumer<? super RegistrationEmail> after) {
    return Consumer.super.andThen(after);
  }
}
