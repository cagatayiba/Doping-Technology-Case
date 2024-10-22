package com.example.demo.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AddQuestionRequest(
        @NotNull
        String content,
        @NotNull
        @Size(min = 1, max = 10)
        List<AddAnswerOptionRequest> answerOptions
) {
}
