package com.example.memorial_application.domain.service.gRPC;

import com.example.grpc.*;
import com.example.memorial_application.domain.domain.MemorialApplication;
import com.example.memorial_application.domain.service.exception.AlreadyMemorializedCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcClientService {
  private static String MEMORIALIZING = "MEMORIALIZING";
  @GrpcClient("anime-server")
  private GetCharacterServiceGrpc.GetCharacterServiceBlockingStub getCharacterServiceBlockingStub;


  public String getCharacterName(MemorialApplication memorialApplication) {
    Long characterId = memorialApplication.getCharacterId();
    GetCharacterResponse response = getCharacter(characterId);
    String name = response.getName();
    return name;
  }

  private GetCharacterResponse getCharacter(Long characterId) {
    GetCharacterRequest getCharacterRequest = GetCharacterRequest.newBuilder()
            .setCharacterId(characterId)
            .build();
    GetCharacterResponse getCharacterResponse = getCharacterServiceBlockingStub.getCharacter(getCharacterRequest);
    return getCharacterResponse;
  }

  public void validateNotAlreadyMemorialized(Long characterId) {
    GetCharacterResponse response = getCharacter(characterId);
    String state = response.getState();
    if (MEMORIALIZING.equals(state)) throw new AlreadyMemorializedCharacter("Already memorialized character");
  }
}
