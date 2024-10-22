package com.example.demo.domain.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TestResultDto(
        UUID testId,
        String testName,
        Integer numberOfCorrectAnswers,
        Integer numberOfWrongAnswers,
        Integer numberOfUnansweredQuestions
) {
}
