package com.example.memorial_application.domain.service;

import com.example.memorial_application.domain.domain.MemorialApplicationLikes;
import com.example.memorial_application.domain.domain.MemorialApplicationLikesId;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationLikesMapper;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationMapper;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemorialApplicationLikesService {
  private final MemorialApplicationLikesRepository memorialApplicationLikesRepository;
  private final MemorialApplicationLikesMapper memorialApplicationLikesMapper;

  public void like(String userId, Long memorialApplicationId) {
    MemorialApplicationLikesId memorialApplicationLikesId = memorialApplicationLikesMapper.toMemorialApplicationLikeId(memorialApplicationId, userId);
    memorialApplicationLikesRepository.findById(memorialApplicationLikesId)
            .map((memorialApplicationLike) -> deleteMemorialApplicationLike(memorialApplicationLike))
            .orElseGet(() -> saveMemorialApplicationLike(memorialApplicationLikesId));
  }

  private MemorialApplicationLikes deleteMemorialApplicationLike(MemorialApplicationLikes memorialApplicationLike) {
    memorialApplicationLikesRepository.delete(memorialApplicationLike);
    return memorialApplicationLike;
  }

  private MemorialApplicationLikes saveMemorialApplicationLike(MemorialApplicationLikesId memorialApplicationLikesId) {
    MemorialApplicationLikes memorialApplicationLike = memorialApplicationLikesMapper.toMemorialApplicationLike(memorialApplicationLikesId);
