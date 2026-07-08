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
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @ManyToMany(
      mappedBy = "users",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Course> courses;
}
