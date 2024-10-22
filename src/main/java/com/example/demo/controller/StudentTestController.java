package com.example.demo.controller;

import com.example.demo.domain.response.StartTestResponse;
import com.example.demo.service.StudentTestManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student/test")
@RequiredArgsConstructor
public class StudentTestController {

    private final StudentTestManagementService studentTestManagementService;

    @PostMapping("/{studentId}/{testId}/start")
    public ResponseEntity<StartTestResponse> startTest(@PathVariable UUID studentId, @PathVariable UUID testId) {
        return ResponseEntity.ok(studentTestManagementService.startTest(studentId, testId));
    }
}
