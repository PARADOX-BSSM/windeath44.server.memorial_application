package com.example.memorial_application.domain.service;

import com.example.grpc.CreateCharacterRequest;
import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.domain.mapper.MemorialApplicationMapper;
import com.example.memorial_application.domain.domain.repository.MemorialApplicationRepository;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateRequest;
import com.example.memorial_application.domain.presentation.dto.request.TestMemorialApplicationCreateRequest;
import com.example.memorial_application.domain.service.gRPC.GrpcClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemorialApplicationService {
  private final MemorialApplicationRepository memorialApplicationRepository;
  private final MemorialApplicationMapper memorialApplicationMapper;
  private final GrpcClientService grpcClient;

  public void apply(String userId, TestMemorialApplicationCreateRequest request) {
    Long animeId = request.animeId();
    String name = request.name();
    String animeContent = request.animeContent();
    String deathReason = request.death_reason();
    Long lifeTime = request.lifeTime();
    String content = request.content();

    CreateCharacterRequest createCharacterRequest = CreateCharacterRequest.newBuilder()
            .setAnimeId(animeId)
            .setName(name)
            .setContent(animeContent)
            .setDeathReason(deathReason)
            .setLifeTime(lifeTime)
            .build();

    Long characterId = grpcClient.createCharacter(createCharacterRequest);
    MemorialApplication memorialApplication = memorialApplicationMapper.toEntity(userId, characterId, content);
    memorialApplicationRepository.save(memorialApplication);
  }
  
}
