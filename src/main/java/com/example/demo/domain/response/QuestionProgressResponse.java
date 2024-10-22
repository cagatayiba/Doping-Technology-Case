package com.example.demo.domain.response;

import lombok.Builder;

@Builder
public record QuestionProgressResponse(
        Integer questionNumber,
        QuestionProgressState questionState
) {
}
