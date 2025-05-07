package com.example.memorial_application.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
@Getter
@Setter
public class KafkaProperties {
  private String bootstrap_servers;
  private Consumer consumer;

  @Getter
  @Setter
  public static class Consumer {
    private String group_id;
    private String auto_offset_reset;

  }

}
