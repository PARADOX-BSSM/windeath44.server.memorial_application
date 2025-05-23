package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.domain.presentation.dto.ResponseDtoMapper;
import com.example.memorial_application.domain.presentation.dto.response.ResponseDto;
import com.example.memorial_application.domain.service.MemorialApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/memorials/application/likes")
public class MemorialApplicationLikesController {
  private final MemorialApplicationService memorialApplicationService;
  private final ResponseDtoMapper responseDtoMapper;

  @PostMapping("/{memorial-application-id}")
  public ResponseEntity<ResponseDto<Void>> like(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.like(userId, memorialApplicationId);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("like memorial application", null);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
  }

  @DeleteMapping("/{memorial-application-id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseEntity<ResponseDto<Void>> unlike(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.unlike(userId, memorialApplicationId);
    ResponseDto<Void> responseDto = responseDtoMapper.toResponseDto("unlike memorial application", null);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(responseDto);
  }

}


