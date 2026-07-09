package com.example.asyncmail.service;

import com.example.asyncmail.endpoint.event.EventProducer;
import com.example.asyncmail.endpoint.event.model.RegistrationEmail;
import com.example.asyncmail.repository.CourseRepository;
import com.example.asyncmail.repository.UserRepository;
import com.example.asyncmail.repository.model.Course;
import com.example.asyncmail.repository.model.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
  private final EventProducer<RegistrationEmail> eventProducer;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final FileService fileService;

  @SneakyThrows
  @Transactional
  public void register(UUID courseId, UUID userId) {
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new NotFoundException("Course not found"));
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    if (course.getUsers().contains(user)) {
      throw new BadRequestException("User already exists");
    }

    List<User> users = new ArrayList<>(course.getUsers());
    users.add(user);
    course.setUsers(users);

    courseRepository.save(course);

    eventProducer.accept(
        List.of(
            RegistrationEmail.builder()
                .to(user.getEmail())
                .registrationFileURI(fileService.uploadRegistrationPDF(user, course))
                .build()));
  }
}
