package com.example.demo.domain.response;

import java.util.List;
import java.util.UUID;

public record QuestionResponse(
        Integer number,
        String content,
        UUID chosenOptionId,
        List<AnswerOptionResponse> answerOptions
) {
}
