package com.example.demo.domain.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ContinueTestResponse(
        String testName,
        List<QuestionProgressResponse> testProgress,
        QuestionResponse firstQuestion
) {
}
