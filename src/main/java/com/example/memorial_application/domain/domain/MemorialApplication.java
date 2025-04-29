package com.example.memorial_application.domain.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemorialApplication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memorialApplicationId;

  private String userId;
  private Long characterId;
  private String content;
  @CreatedDate
  private LocalDateTime createdAt;
  private Long likes;
  @Enumerated(EnumType.STRING)
  private MemorialApplicationState state;

  @PrePersist
  public void init() {
    this.likes = 0L;
  }
}
