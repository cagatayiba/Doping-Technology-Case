package com.example.demo.domain.response;

import lombok.Builder;
import java.util.List;

@Builder
public record AddQuestionResponse(
        TestResponse test,
        List<QuestionAdminResponse> questions
) {
}
