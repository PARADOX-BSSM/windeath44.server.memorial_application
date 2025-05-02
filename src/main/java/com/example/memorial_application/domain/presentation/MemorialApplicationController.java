package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateRequest;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateWithCharacterRequest;
import com.example.memorial_application.domain.service.MemorialApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/memorials/application")
public class MemorialApplicationController {
  private final MemorialApplicationService memorialApplicationService;

  @PostMapping ("/withCharacter")// 캐릭터가 존재하지 않을 경우 -> 캐릭터와 추모관 신청의 생성 시간이 같다.
  @ResponseStatus(HttpStatus.CREATED)
  public void applyWithCharacter(@RequestHeader("user-id") String userId, @RequestBody MemorialApplicationCreateWithCharacterRequest request) {
    memorialApplicationService.applyWithCharacter(userId, request);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void apply(@RequestHeader("user-id") String userId, @RequestBody MemorialApplicationCreateRequest request) {
    Long characterId = request.characterId();
    String content = request.content();
    memorialApplicationService.apply(userId, characterId, content);
  }


  @GetMapping
  public ResponseEntity<List<MemorialApplicationResponse>> findAll() {
    List<MemorialApplicationResponse> memorialApplicationResponse = memorialApplicationService.findAll();
    return ResponseEntity.ok(memorialApplicationResponse);
  }

  @GetMapping("/{memorial-application-id}")
  public ResponseEntity<MemorialApplicationResponse> findById(@PathVariable("memorial-application-id") Long memorialApplicationId) {
    MemorialApplicationResponse memorialApplicationResponse = memorialApplicationService.findById(memorialApplicationId);
    return ResponseEntity.ok(memorialApplicationResponse);
  }

  @PatchMapping("/{memorial-application-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.reject(memorialApplicationId);
  }
}
