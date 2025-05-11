package com.example.memorial_application.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public void send(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }

}
