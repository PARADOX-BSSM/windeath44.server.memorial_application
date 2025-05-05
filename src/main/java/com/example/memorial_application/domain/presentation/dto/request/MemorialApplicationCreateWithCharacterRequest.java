package com.example.memorial_application.domain.presentation.dto.request;

public record MemorialApplicationCreateWithCharacterRequest (
        Long animeId,
        String name,
        String characterContent,
        Long lifeTime,
        String death_reason,
        String content
) {
}
