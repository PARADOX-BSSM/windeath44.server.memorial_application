package com.example.memorial_application.domain.presentation.dto.request;

public record MemorialApplicationCreateRequest (
        Long characterId,
        String content
) {
}
