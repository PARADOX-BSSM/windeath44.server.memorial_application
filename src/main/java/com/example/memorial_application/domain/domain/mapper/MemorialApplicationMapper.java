package com.example.memorial_application.domain.domain.mapper;


import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.MemorialApplicationState;
import org.springframework.stereotype.Component;

@Component
public class MemorialApplicationMapper {

  public MemorialApplication toEntity(String userId, Long characterId, String content) {
    return MemorialApplication.builder()
            .userId(userId)
            .characterId(characterId)
            .content(content)
            .state(MemorialApplicationState.PENDING)
            .build();

  }
}
