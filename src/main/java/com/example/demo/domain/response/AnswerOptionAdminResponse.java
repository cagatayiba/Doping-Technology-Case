package com.example.demo.domain.response;

public record AnswerOptionAdminResponse(
        Character label,
        String content,
        Boolean isCorrectOption
) {
}
