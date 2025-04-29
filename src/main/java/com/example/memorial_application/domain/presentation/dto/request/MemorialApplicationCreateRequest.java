package com.example.memorial_application.domain.presentation.dto.request;

public record MemorialApplicationCreateRequest (
        Long animeId,
        String name,
        String animeContent,
        Long lifeTime,
        String death_reason,
        String content
) {
}
