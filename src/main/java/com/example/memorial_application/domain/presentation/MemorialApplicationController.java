package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.MemorialApplication;
import com.example.memorial_application.domain.presentation.dto.ResponseDtoMapper;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateRequest;
import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateWithCharacterRequest;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationResponse;
import com.example.memorial_application.domain.presentation.dto.response.MemorialApplicationWithCursorResponse;
import com.example.memorial_application.domain.presentation.dto.response.ResponseDto;
import com.example.memorial_application.domain.service.MemorialApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.memorial_application.domain.presentation.dto.response.MemorialAllApplicationResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/memorials/application")
public class MemorialApplicationController {
  private final MemorialApplicationService memorialApplicationService;
  private final ResponseDtoMapper responseDtoMapper;
  private final MemorialApplication memorialApplication;

  @PostMapping ("/apply/withCharacter")// 캐릭터가 존재하지 않을 경우 -> 캐릭터와 추모관 신청의 생성 시점이 같다.
  public ResponseEntity<ResponseDto<Void>> applyWithCharacter(@RequestHeader("user-id") String userId, @RequestBody MemorialApplicationCreateWithCharacterRequest request) {
    memorialApplicationService.applyWithCharacter(userId, request);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("apply memorial application with character", null);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
  }

  @PostMapping("/apply")
  public ResponseEntity<ResponseDto<Void>> apply(@RequestHeader("user-id") String userId, @RequestBody MemorialApplicationCreateRequest request) {
    Long characterId = request.characterId();
    String content = request.content();
    memorialApplicationService.apply(userId, characterId, content);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("apply memorial application", null);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
  }

  @GetMapping("/page/{cursor-id}/{size}")
  public ResponseEntity<ResponseDto<List<MemorialApplicationWithCursorResponse>>> findByCursor(@PathVariable("cursor-id") Long cursorId, @PathVariable("size") Long size) {
    List<MemorialApplicationWithCursorResponse> memorialApplicationResponse = memorialApplicationService.findByCursor(cursorId, size);
    ResponseDto<List<MemorialApplicationWithCursorResponse>> responseDto = responseDtoMapper.toResponseDto("find memorials application with cursor", memorialApplicationResponse);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<List<MemorialAllApplicationResponse>>> findAll() {
    List<MemorialAllApplicationResponse> memorialApplicationResponse = memorialApplicationService.findAll();
    ResponseDto<List<MemorialAllApplicationResponse>> responseDto = responseDtoMapper.toResponseDto("find memorials applications", memorialApplicationResponse);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/{memorial-application-id}")
  public ResponseEntity<ResponseDto<MemorialApplicationResponse>> findById(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    MemorialApplicationResponse memorialApplicationResponse = memorialApplicationService.findById(memorialApplicationId, userId);
    ResponseDto<MemorialApplicationResponse> responseDto = responseDtoMapper.toResponseDto("find memorial application", memorialApplicationResponse);
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/approve/{memorial-application-id}/admin")
  public ResponseEntity<ResponseDto<Void>> approve(@PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.approve(memorialApplicationId);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("approve memorial application", null);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(responseDto);
  }
  
  @PatchMapping("/cancel/{memorial-application-id}")
  public ResponseEntity<ResponseDto<Void>> cancel(@PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.reject(memorialApplicationId);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("cancel memorial application", null);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(responseDto);
  }
}
