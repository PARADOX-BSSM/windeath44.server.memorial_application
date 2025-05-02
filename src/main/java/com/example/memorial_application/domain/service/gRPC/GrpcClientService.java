package com.example.memorial_application.domain.service.gRPC;

import com.example.grpc.CreateCharacterRequest;
import com.example.grpc.CreateCharacterResponse;
import com.example.grpc.CreateCharacterServiceGrpc;
import com.example.memorial_application.domain.domain.MemorialApplication;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcClientService {
  @GrpcClient("anime-server")
  private CreateCharacterServiceGrpc.CreateCharacterServiceBlockingStub createCharacterServiceBlockingStub;

  public Long createCharacter(CreateCharacterRequest request) {
    Long characterId = sendToCreateCharacter(request);
    return characterId;
  }

  private Long sendToCreateCharacter(CreateCharacterRequest request) {
    CreateCharacterResponse response = getCreateCharacterResponse(request);
    Long characterId = response.getCharacterId();
    return characterId;
  }


  private CreateCharacterResponse getCreateCharacterResponse(CreateCharacterRequest request) {
    CreateCharacterResponse response = createCharacterServiceBlockingStub.createCharacter(request);
    return response;
  }

  public String getCharacterName(MemorialApplication memorialApplication) {
    Long characterId = memorialApplication.getCharacterId();
    // GetCharacterResponse response = getCharacter(characterId);
    // String name = response.getName();
    String name = null;
    return name;
  }
}
