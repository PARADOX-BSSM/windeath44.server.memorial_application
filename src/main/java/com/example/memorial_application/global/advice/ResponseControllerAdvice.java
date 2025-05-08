package com.example.memorial_application.global.advice;

import com.example.memorial_application.global.error.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    try {
      ResponseEntity<?> responseBody = (ResponseEntity<?>) body;
      String message = checkStatusCode(responseBody.getStatusCode());
      return new ResponseDto<>(message, responseBody);
    } catch (ClassCastException e) {
      return new ResponseDto<>("failed", body);
    }
  }

  private String checkStatusCode(HttpStatusCode statusCode) {
    int value = statusCode.value();
    String message = "success";
    if(ErrorCode.contains(value)) {
      message = "failed";
    }
    return message;
  }

  private record ResponseDto<T> (
          String message,
          T data
  ) {
  }

}
