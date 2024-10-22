package com.example.demo.domain.response;

public record QuestionProgressResponse(
        Integer questionNumber,
        QuestionProgressState questionState
) {
}
