package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.domain.presentation.dto.request.MemorialApplicationCreateRequest;
import com.example.memorial_application.domain.presentation.dto.request.TestMemorialApplicationCreateRequest;
import com.example.memorial_application.domain.service.MemorialApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemorialApplicationController {
  private final MemorialApplicationService memorialApplicationService;
//  @PostMapping("/memorials/application")
//  @ResponseStatus(HttpStatus.CREATED)
//  public void apply(@RequestHeader("user-id") String userId, @RequestBody MemorialApplicationCreateRequest request) {
//    memorialApplicationService.apply(userId, request.animeId(), request.content(), request.name(), request.animeContent(), request.death_reason(), request.lifeTime());
//  }

  @PostMapping("/memorials/application")
  @ResponseStatus(HttpStatus.CREATED)
  public void apply(@RequestBody TestMemorialApplicationCreateRequest request) {
    memorialApplicationService.apply(request.userId(), request);
  }
}
