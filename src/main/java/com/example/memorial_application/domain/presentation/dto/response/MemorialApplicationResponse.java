package com.example.memorial_application.domain.presentation.dto.response;

import com.example.memorial_application.domain.domain.MemorialApplicationState;

import java.time.LocalDateTime;
public record MemorialApplicationResponse (
        // 유저 정보
        String userId,
        // 캐릭터 정보
        Long characterId,
        //신청 정보
        String content,
        LocalDateTime createdAt,
        MemorialApplicationState state,
        Long likes,
        boolean userDidLike
) {
}