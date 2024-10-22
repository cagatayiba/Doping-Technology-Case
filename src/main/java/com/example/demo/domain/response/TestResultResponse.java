package com.example.demo.domain.response;

import com.example.demo.domain.dto.TestResultDto;

import java.util.List;

public record TestResultResponse(
        List<TestResultDto> results
) {
}
