package com.example.memorial_application.domain.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MemorialApplicationLikesId {
  @Column(name="memorial_application_id")
  private Long memorialApplicationId;
  @Column(name="user_id")
  private String userId;

}
