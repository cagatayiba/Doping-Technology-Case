package com.example.demo.domain.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record QuestionResultResponse(
        UUID questionId,
        Integer questionNumber,
        QuestionResult result
) {
}
