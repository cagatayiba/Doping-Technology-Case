package com.example.demo.domain.response;

import lombok.Builder;

@Builder
public record StartTestResponse(
        String testName,
        QuestionResponse firstQuestion
) {
}
