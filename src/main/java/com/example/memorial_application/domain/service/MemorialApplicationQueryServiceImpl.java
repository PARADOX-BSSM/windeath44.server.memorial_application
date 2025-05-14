package com.example.memorial_application.domain.service;

import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.MemorialApplicationLikesId;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationLikesMapper;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationMapper;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationLikesRepository;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationRepository;
import com.example.memorial_application.domain.presentation.dto.response.MemorialAllApplicationResponse;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationWithCursorResponse;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemorialApplicationQueryServiceImpl implements MemorialApplicationService {
  private final MemorialApplicationRepository memorialApplicationRepository;
  private final MemorialApplicationMapper memorialApplicationMapper;

  private final MemorialApplicationLikesRepository memorialApplicationLikesRepository;
  private final MemorialApplicationLikesMapper memorialApplicationLikesMapper;

  public List<MemorialAllApplicationResponse> findAll() {
    List<MemorialAllApplicationResponse> memorialApplicationList = getMemorialApplicationList();
    return memorialApplicationList;
  }

  private List<MemorialAllApplicationResponse> getMemorialApplicationList() {
    List<MemorialAllApplicationResponse> memorialApplicationResponseList = memorialApplicationRepository.findAllSortByLikes()
            .stream()
            .map(memorialApplicationMapper::toMemorialAllApplicationResponse)
            .toList();
    return memorialApplicationResponseList;
  }

  private MemorialApplication findMemorialApplicationById(Long memorialApplicationId) {
    return memorialApplicationRepository.findById(memorialApplicationId)
            .orElseThrow(NotFoundMemorialApplicationException::getInstance);
  }



  private boolean isUserDidLikes(String userId, Long memorialApplicationId) {
    MemorialApplicationLikesId memorialApplicationLikesId = memorialApplicationLikesMapper.toMemorialApplicationLikeId(memorialApplicationId, userId);
    boolean userDidLikes = memorialApplicationLikesRepository.existsById(memorialApplicationLikesId);
    return userDidLikes;
  }


  public List<MemorialApplicationWithCursorResponse> findByCursor(Long cursorId, Long size) {
    List<MemorialApplicationWithCursorResponse> memorialApplicationsList = memorialApplicationRepository.findAllByCursor(cursorId, size)
            .stream()
            .map(memorialApplicationMapper::toMemorialApplicationWithCursor)
            .toList();
    return memorialApplicationsList;
  }

  public MemorialApplicationResponse findByCharacterId(Long characterId, String userId) {
    MemorialApplication memorialApplication = memorialApplicationRepository.findByCharacterId(characterId)
            .orElseThrow(NotFoundMemorialApplicationException::getInstance);
    boolean userDidLikes = userId != null && isUserDidLikes(userId, memorialApplication.getMemorialApplicationId());
    MemorialApplicationResponse memorialApplicationResponse = memorialApplicationMapper.toMemorialApplicationResponse(memorialApplication, userDidLikes);
    return memorialApplicationResponse;
  }

}
