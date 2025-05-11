package com.example.memorial_application.domain.presentation.dto;

import com.example.memorial_application.domain.presentation.dto.response.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ResponseDtoMapper {

  public <T> ResponseDto<T> toResponseDto(String message, T data) {
    return new ResponseDto<>(message, data);
  }

}
