package com.example.memorial_application.global.utils.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApprovedMemorialApplicationMessage extends KafkaMessage {
  private Long memorialApplicationId;
}
