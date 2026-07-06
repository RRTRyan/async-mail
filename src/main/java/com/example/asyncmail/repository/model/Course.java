package com.example.asyncmail.repository.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "seats")
  private Integer seats;

  @ManyToMany
  @JoinTable(
      name = "registration",
      joinColumns = @JoinColumn(name = "course_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
  private List<User> users;
}
