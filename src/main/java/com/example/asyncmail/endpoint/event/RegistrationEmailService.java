package com.example.asyncmail.endpoint.event;

import com.example.asyncmail.endpoint.event.model.RegistrationEmail;
import com.example.asyncmail.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class RegistrationEmailService implements Consumer<RegistrationEmail> {
    private final Mailer mailer;

    @Override
    @SneakyThrows
    public void accept(RegistrationEmail registrationEmail) {
        InternetAddress emailAdress = new InternetAddress(registrationEmail.getTo());
    }

    @Override
    public Consumer<RegistrationEmail> andThen(Consumer<? super RegistrationEmail> after) {
        return Consumer.super.andThen(after);
    }
}
