package com.example.memorial_application.domain.service;

import com.example.grpc.CreateCharacterRequest;
import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationMapper;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationRepository;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateRequest;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateWithCharacterRequest;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplication;
import com.example.memorial_application.domain.service.gRPC.GrpcClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemorialApplicationService {
  private final MemorialApplicationRepository memorialApplicationRepository;
  private final MemorialApplicationMapper memorialApplicationMapper;
  private final GrpcClientService grpcClient;

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
    CreateCharacterRequest createCharacterRequest = CreateCharacterRequest.newBuilder()
            .setAnimeId(animeId)
            .setName(name)
            .setContent(characterContent)
            .setDeathReason(deathReason)
            .setLifeTime(lifeTime)
            .build();
    Long characterId = grpcClient.createCharacter(createCharacterRequest);
    return characterId;
  }

  public void apply(String userId, Long characterId, String content) {
    MemorialApplication memorialApplication = memorialApplicationMapper.toMemorialApplication(userId, characterId, content);
    memorialApplicationRepository.save(memorialApplication);
  }


  public List<MemorialApplicationResponse> findAll() {
    List<MemorialApplicationResponse> memorialApplicationList = getMemorialApplicationList();
    return memorialApplicationList;
  }

  private List<MemorialApplicationResponse> getMemorialApplicationList() {
    return memorialApplicationRepository.findAll()
            .stream()
            .map((memorialApplication) -> {
              String name = grpcClient.getCharacterName(memorialApplication);
              MemorialApplicationResponse memorialAPplicationResponse = memorialApplicationMapper.toMemorialApplicationResponse(memorialApplication, name);
              return memorialAPplicationResponse;
            })
            .toList();
  }

  public void reject(Long memorialApplicationId) {
    MemorialApplication memorialApplication = findMemorialApplicationById(memorialApplicationId);
    memorialApplication.reject();
  }


  private MemorialApplication findMemorialApplicationById(Long memorialApplicationId) {
    return memorialApplicationRepository.findById(memorialApplicationId)
            .orElseThrow(() -> new NotFoundMemorialApplication("Not found memorial application"));
  }

  public MemorialApplicationResponse findById(Long memorialApplicationId) {
    MemorialApplication memorialApplication = findMemorialApplicationById(memorialApplicationId);
    String name = grpcClient.getCharacterName(memorialApplication);
    MemorialApplicationResponse memorialApplicationResponse = memorialApplicationMapper.toMemorialApplicationResponse(memorialApplication, name);
    return memorialApplicationResponse;
  }

}
