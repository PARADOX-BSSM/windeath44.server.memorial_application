package com.example.memorial_application.domain.domain.mapper;


import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.MemorialApplicationLikes;
import com.example.memorial_application.domain.domain.MemorialApplicationLikesId;
import com.example.memorial_application.domain.domain.MemorialApplicationState;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;
import com.example.memorial_application.domain.service.MemorialApplicationLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MemorialApplicationMapper {
  private final MemorialApplicationLikesService memorialApplicationLikesService;

  public MemorialApplication toMemorialApplication(String userId, Long characterId, String content) {
    return MemorialApplication.builder()
            .userId(userId)
            .characterId(characterId)
            .content(content)
            .state(MemorialApplicationState.PENDING)
            .build();

  }

  public MemorialApplicationResponse toMemorialApplicationResponse(MemorialApplication memorialApplication, String name) {
    Long memorialApplicationId = memorialApplication.getMemorialApplicationId();
    String userId = memorialApplication.getUserId();
    Long characterId = memorialApplication.getCharacterId();
    String content = memorialApplication.getContent();
    LocalDateTime createdAt = memorialApplication.getCreatedAt();
    Long likes = memorialApplicationLikesService.countLikes(memorialApplicationId);

    return new MemorialApplicationResponse(userId, characterId, name, content, createdAt, likes);
  }

}
