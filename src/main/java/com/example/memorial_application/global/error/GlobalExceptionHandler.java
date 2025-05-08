package com.example.memorial_application.global.error;

import com.example.memorial_application.global.error.exception.ErrorCode;
import com.example.memorial_application.global.error.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ErrorResponse> customGlobalExceptionHandler(GlobalException e) {
    final ErrorCode errorCode = e.getErrorCode();
    log.error(errorCode.getMessage());
    int errorStatus = errorCode.getStatus();
    return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorStatus));
  }

}
