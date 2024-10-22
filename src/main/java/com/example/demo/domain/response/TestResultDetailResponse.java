package com.example.demo.domain.response;

import com.example.demo.domain.dto.TestResultDto;
import lombok.Builder;

import java.util.List;

@Builder
public record TestResultDetailResponse(
    TestResultDto testResult,
    List<QuestionResultResponse> questionResults
) {
}
