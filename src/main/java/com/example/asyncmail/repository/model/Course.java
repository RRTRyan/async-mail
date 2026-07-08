package com.example.asyncmail.repository.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "registration",
      joinColumns = @JoinColumn(name = "course_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<User> users;
}
