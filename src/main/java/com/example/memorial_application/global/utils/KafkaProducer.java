package com.example.memorial_application.global.utils;

import com.example.memorial_application.global.utils.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
  private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

  public void send(String topic, KafkaMessage message) {
    kafkaTemplate.send(topic, message);
  }

}
