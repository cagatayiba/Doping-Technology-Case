package com.example.demo.controller;

import com.example.demo.domain.request.AnswerQuestionRequest;
import com.example.demo.domain.response.ContinueTestResponse;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.domain.response.StartTestResponse;
import com.example.demo.service.StudentAnswerService;
import com.example.demo.service.StudentTestManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-test")
@RequiredArgsConstructor
public class StudentTestController {

    private final StudentTestManagementService studentTestManagementService;
    private final StudentAnswerService studentAnswerService;

    @PutMapping("/{studentId}/{testId}/start")
    public ResponseEntity<StartTestResponse> startTest(@PathVariable UUID studentId, @PathVariable UUID testId) {
        return ResponseEntity.ok(studentTestManagementService.startTest(studentId, testId));
    }

    @GetMapping("/{studentId}/{testId}/get-question/{questionNumber}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable UUID studentId, @PathVariable UUID testId, @PathVariable int questionNumber) {
        return ResponseEntity.ok(studentTestManagementService.getQuestion(studentId, testId, questionNumber));
    }

    @PostMapping("/{studentId}/{testId}/answer-question")
    public ResponseEntity<Object> answerQuestion(@PathVariable UUID studentId, @PathVariable UUID testId, @RequestBody AnswerQuestionRequest answerQuestionRequest) {
        studentAnswerService.answerQuestion(studentId, testId, answerQuestionRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/{testId}/continue")
    public ResponseEntity<ContinueTestResponse> continueTest(@PathVariable UUID studentId, @PathVariable UUID testId) {
        return ResponseEntity.ok(studentTestManagementService.continueTest(studentId, testId));
    }
}
