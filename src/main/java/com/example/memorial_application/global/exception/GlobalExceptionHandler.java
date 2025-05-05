package com.example.memorial_application.global.exception;

import com.example.memorial_application.domain.service.exception.AlreadyMemorialApplicationLikes;
import com.example.memorial_application.domain.service.exception.AlreadyMemorializedCharacter;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplication;
import com.example.memorial_application.domain.service.exception.NotFoundMemorialApplicationLikes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(AlreadyMemorializedCharacter.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void alreadyMemorializedCharacterHandler(AlreadyMemorializedCharacter e) {
    log.error(e.getMessage());
  }
  @ExceptionHandler(NotFoundMemorialApplication.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFoundMemorialApplication(NotFoundMemorialApplication e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(AlreadyMemorialApplicationLikes.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public void alreadyMemorialApplicationLikesHandler(AlreadyMemorialApplicationLikes e) {
    log.error(e.getMessage());
  }

  @ExceptionHandler(NotFoundMemorialApplicationLikes.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void notFoundMemorialApplicationLikesHandler(NotFoundMemorialApplicationLikes e) {
    log.error(e.getMessage());
  }


}
