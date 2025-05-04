package com.example.memorial_application.global.exception;

import com.example.memorial_application.domain.service.exception.AlreadyMemorializedCharacter;
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
  public void alreadyMemorializedCharacterHanlder(AlreadyMemorializedCharacter alreadyMemorializedCharacter) {
    log.error(alreadyMemorializedCharacter.getMessage());
  }
}
