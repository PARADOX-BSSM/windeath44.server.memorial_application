package com.example.memorial_application.domain.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemorialApplication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memorialApplicationId;

  private String userId;
  private Long characterId;
  private String content;
  @CreatedDate
  private LocalDateTime createdAt;
  @Enumerated(EnumType.STRING)
  private MemorialApplicationState state;

  public void reject() {
    this.state = MemorialApplicationState.REJECTED;
  }
}
