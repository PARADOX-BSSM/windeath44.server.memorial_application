package com.example.memorial_application.domain.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MemorialApplicationLikes {
  @EmbeddedId
  private MemorialApplicationLikesId id;
}
