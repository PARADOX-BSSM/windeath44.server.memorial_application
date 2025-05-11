package com.example.memorial_application.domain.presentation.dto.response;

import java.util.List;

public record ResponseDto<T> (
        String message,
        T data
) {

}
