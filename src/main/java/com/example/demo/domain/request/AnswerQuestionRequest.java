package com.example.demo.domain.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AnswerQuestionRequest(
        @NotNull UUID questionId,
        @NotNull UUID chosenOption
) {
}
