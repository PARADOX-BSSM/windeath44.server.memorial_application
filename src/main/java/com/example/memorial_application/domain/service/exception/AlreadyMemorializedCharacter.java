package com.example.memorial_application.domain.service.exception;

public class AlreadyMemorializedCharacter extends RuntimeException {
  public AlreadyMemorializedCharacter(String s) {
    super(s);
  }
}
