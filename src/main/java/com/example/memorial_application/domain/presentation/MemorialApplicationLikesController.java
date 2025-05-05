package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.domain.service.MemorialApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/memorials/application/likes")
public class MemorialApplicationLikesController {
  private final MemorialApplicationService memorialApplicationService;

  @PostMapping("/{memorial-application-id}")
  @ResponseStatus(HttpStatus.CREATED)
  public void like(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.like(userId, memorialApplicationId);
  }

  @DeleteMapping("/{memorial-application-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unlike(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationService.unlike(userId, memorialApplicationId);
  }

}


