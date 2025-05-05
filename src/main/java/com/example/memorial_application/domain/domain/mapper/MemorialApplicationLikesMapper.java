package com.example.memorial_application.domain.domain.mapper;

import com.example.memorial_application.domain.domain.MemorialApplicationLikes;
import com.example.memorial_application.domain.domain.MemorialApplicationLikesId;
import org.springframework.stereotype.Component;

@Component
public class MemorialApplicationLikesMapper {
  public MemorialApplicationLikesId toMemorialApplicationLikeId(Long memorialApplicationId, String userId) {
    return new MemorialApplicationLikesId(memorialApplicationId, userId);
  }

  public MemorialApplicationLikes toMemorialApplicationLike(MemorialApplicationLikesId memorialApplicationLikesId) {
    return new MemorialApplicationLikes(memorialApplicationLikesId);
  }
}
