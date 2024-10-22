package com.example.demo.controller;

import com.example.demo.domain.response.TestResultDetailResponse;
import com.example.demo.domain.response.TestResultResponse;
import com.example.demo.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test-result")
@RequiredArgsConstructor
public class TestResultController {

    private final TestResultService testResultService;

    @GetMapping("/{studentId}")
    public TestResultResponse getTestResults(@PathVariable UUID studentId, Pageable pageable) {
        return testResultService.getTestResults(studentId, pageable);
    }

    @GetMapping("/{studentId}/detail/{testId}")
    public TestResultDetailResponse getTestResultDetail(@PathVariable UUID studentId, @PathVariable UUID testId) {
        return testResultService.getTestResultDetail(studentId, testId);
    }
}
