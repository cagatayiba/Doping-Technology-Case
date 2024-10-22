package com.example.demo.domain.response;

import java.util.UUID;

public record AnswerOptionResponse(
        UUID id,
        Character label,
        String content
) {
}
