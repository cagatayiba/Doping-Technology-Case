package com.example.demo.domain.request;

import jakarta.validation.constraints.NotNull;

public record AddAnswerOptionRequest(
        @NotNull Character label,
        @NotNull String content,
        @NotNull Boolean isCorrectOption
) {
}
