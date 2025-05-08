package com.example.memorial_application.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  MEMORIAL_APPLICATION_NOT_FOUND(404, "Memorial application not found."),
  MEMORIAL_APPLICATION_LIKES_NOT_FOUND(404, "Memorial application likes not found"),
  ALREADY_MEMORIALIZED_CHARACTER(400, "Character already memorialized"),
  ALREADY_MEMORIAL_APPLICATION_LIKES(400, "Memorial application already likes"),
  ;

  private int status;
  private String message;

  public static boolean contains(int value) {
    for(ErrorCode errorCode : ErrorCode.values()) {
      int status = errorCode.getStatus();
      if (value == status) {
        return true;
      }
    }
    return false;
  }
}
