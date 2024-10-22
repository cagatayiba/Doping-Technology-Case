package com.example.demo.controller;

import com.example.demo.domain.request.AddQuestionRequest;
import com.example.demo.domain.request.CreateTestRequest;
import com.example.demo.domain.response.AddQuestionResponse;
import com.example.demo.domain.response.TestResponse;
import com.example.demo.service.QuestionService;
import com.example.demo.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<TestResponse> create(@RequestBody @Valid CreateTestRequest createTestRequest){
        return ResponseEntity.ok(testService.createTest(createTestRequest));
    }

    @PostMapping("/{testId}/add-question")
    public ResponseEntity<AddQuestionResponse> addQuestion(@PathVariable UUID testId, @RequestBody @Valid AddQuestionRequest addQuestionRequest){
        return ResponseEntity.ok(questionService.addQuestion(testId, addQuestionRequest));
    }

    @GetMapping("/{testId}/set-ready")
    public ResponseEntity<Object> setReady(@PathVariable UUID testId){
        testService.setReady(testId);
        return ResponseEntity.ok().build();
    }
}
