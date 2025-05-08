package com.example.memorial_application.domain.service;

import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.MemorialApplicationLikes;
import com.example.memorial_application.domain.domain.MemorialApplicationLikesId;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationLikesMapper;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationMapper;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationLikesRepository;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationRepository;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateWithCharacterRequest;
import com.example.memorial_application.domain.presentation.dto.response.MemorialAllApplicationResponse;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;
import com.example.memorial_application.domain.service.exception.AlreadyMemorialApplicationLikesException;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplicationException;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplicationLikesException;
import com.example.memorial_application.domain.service.gRPC.GrpcClientService;
import com.example.memorial_application.global.utils.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemorialApplicationService {
  private final MemorialApplicationRepository memorialApplicationRepository;
  private final MemorialApplicationMapper memorialApplicationMapper;
  private final GrpcClientService grpcClient;
  private final KafkaProducer kafkaProducer;

  private final MemorialApplicationLikesRepository memorialApplicationLikesRepository;
  private final MemorialApplicationLikesMapper memorialApplicationLikesMapper;

  public void applyWithCharacter(String userId, MemorialApplicationCreateWithCharacterRequest request) {
    Long animeId = request.animeId();
    String name = request.name();
    String characterContent = request.characterContent();
    String deathReason = request.death_reason();
    Long lifeTime = request.lifeTime();

    Long characterId = createCharacterAndGetId(animeId, name, characterContent, deathReason, lifeTime);
    String content = request.content();

    apply(userId, characterId, content);
  }

  private Long createCharacterAndGetId(Long animeId, String name, String characterContent, String deathReason, Long lifeTime) {
    Long characterId = null;
    // 오케스트레이션 서버에 create character 요청
    return characterId;
  }

  public void apply(String userId, Long characterId, String content) {
    MemorialApplication memorialApplication = memorialApplicationMapper.toMemorialApplication(userId, characterId, content);
    // 만약 해당 캐릭터가 이미 추모중이라면 apply 실패
    grpcClient.validateNotAlreadyMemorialized(characterId);
    memorialApplicationRepository.save(memorialApplication);
  }


  public List<MemorialAllApplicationResponse> findAll() {
    List<MemorialAllApplicationResponse> memorialApplicationList = getMemorialApplicationList();
    return memorialApplicationList;
  }

  private List<MemorialAllApplicationResponse> getMemorialApplicationList() {
    List<MemorialAllApplicationResponse> memorialApplicationResponseList = memorialApplicationRepository.findAllSortByLikes()
            .stream()
            .map((memorialApplication) -> {
              String name = grpcClient.getCharacterName(memorialApplication);
              MemorialAllApplicationResponse memorialApplicationResponse = memorialApplicationMapper.toMemorialAllApplicationResponse(memorialApplication, name);
              return memorialApplicationResponse;
            })
            .toList();
    return memorialApplicationResponseList;
  }

  @Transactional
  public void reject(Long memorialApplicationId) {
    MemorialApplication memorialApplication = findMemorialApplicationById(memorialApplicationId);
    memorialApplication.reject();
  }

  private MemorialApplication findMemorialApplicationById(Long memorialApplicationId) {
    return memorialApplicationRepository.findById(memorialApplicationId)
            .orElseThrow(NotFoundMemorialApplicationException::getInstance);
  }

  public MemorialApplicationResponse findById(Long memorialApplicationId, String userId) {
    MemorialApplication memorialApplication = findMemorialApplicationById(memorialApplicationId);
    String name = grpcClient.getCharacterName(memorialApplication);
    MemorialApplicationLikesId memorialAPplicationLikesId = memorialApplicationLikesMapper.toMemorialApplicationLikeId(memorialApplicationId, userId);

    boolean userDidLikes = memorialApplicationLikesRepository.existsById(memorialAPplicationLikesId);
    MemorialApplicationResponse memorialApplicationResponse = memorialApplicationMapper.toMemorialApplicationResponse(memorialApplication, name, userDidLikes);
    return memorialApplicationResponse;
  }


  @Transactional
  public void approve(Long memorialApplicationId) {
    MemorialApplication memorialApplication = findMemorialApplicationById(memorialApplicationId);
    memorialApplication.approve();
    // kafka로 오케스트레이션 서버에 memorial application approve 요청 with memorialApplicationId
    kafkaProducer.send("approved-memorial-application", memorialApplicationId.toString());

    // 오케스트레이션 서버에서 kafka로 memorial 서버로 생성 요청
    // 오케스트레이션 서버에서 kafka로 anime 서버의 캐릭터 상태를 'NOT_MEMORIALIZING' -> 'MEMORIALIZING'으로 변환
    // pending 상태의 같은 캐릭터에 대한 요청들을 rejected 상태로 변환
    restMemorialApplicationRejected(memorialApplicationId, memorialApplication);
  }

  private void restMemorialApplicationRejected(Long memorialApplicationId, MemorialApplication memorialApplication) {
    Long characterId = memorialApplication.getCharacterId();
    memorialApplicationRepository.updateStateToRejectedByCharacterId(memorialApplicationId, characterId);
  }

  @Transactional
  public void like(String userId, Long memorialApplicationId) {
    MemorialApplicationLikesId memorialApplicationLikesId = memorialApplicationLikesMapper.toMemorialApplicationLikeId(memorialApplicationId, userId);
    boolean existsMemorialApplicationLikes = memorialApplicationLikesRepository.existsById(memorialApplicationLikesId);
    if (existsMemorialApplicationLikes) {
      throw AlreadyMemorialApplicationLikesException.getInstance();
    }
    saveMemorialApplicationLike(memorialApplicationLikesId, memorialApplicationId);
  }

  @Transactional
  public void unlike(String userId, Long memorialApplicationId) {
    MemorialApplicationLikesId memorialApplicationLikesId = memorialApplicationLikesMapper.toMemorialApplicationLikeId(memorialApplicationId, userId);
    MemorialApplicationLikes memorialApplicationLikes = memorialApplicationLikesRepository.findById(memorialApplicationLikesId)
                    .orElseThrow(NotFoundMemorialApplicationLikesException::getInstance);
    deleteMemorialApplicationLike(memorialApplicationLikes, memorialApplicationId);
  }

  private MemorialApplicationLikes deleteMemorialApplicationLike(MemorialApplicationLikes memorialApplicationLike, Long memorialApplicationId) {
    memorialApplicationLikesRepository.delete(memorialApplicationLike);
    memorialApplicationRepository.decrementLikes(memorialApplicationId);
    return memorialApplicationLike;
  }

  private MemorialApplicationLikes saveMemorialApplicationLike(MemorialApplicationLikesId memorialApplicationLikesId, Long memorialApplicationId) {
    MemorialApplicationLikes memorialApplicationLike = memorialApplicationLikesMapper.toMemorialApplicationLike(memorialApplicationLikesId);
    memorialApplicationLikesRepository.save(memorialApplicationLike);
    memorialApplicationRepository.incrementLikes(memorialApplicationId);
    return memorialApplicationLike;
  }
}
