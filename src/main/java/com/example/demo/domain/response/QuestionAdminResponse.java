package com.example.demo.domain.response;

import java.util.List;
import java.util.UUID;

public record QuestionAdminResponse(
        UUID id,
        Integer number,
        String content,
        List<AnswerOptionResponse> answers
) {
}
