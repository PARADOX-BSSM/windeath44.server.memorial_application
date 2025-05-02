package com.example.memorial_application.domain.presentation;

import com.example.memorial_application.domain.service.MemorialApplicationLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/memorials/application/likes")
public class MemorialApplicationLikesController {
  private final MemorialApplicationLikesService memorialApplicationLikesService;

  @PutMapping(value="/{memorial-application-id}")
  @ResponseStatus(HttpStatus.CREATED)
  public void like(@RequestHeader("user-id") String userId, @PathVariable("memorial-application-id") Long memorialApplicationId) {
    memorialApplicationLikesService.like(userId, memorialApplicationId);
  }


}


