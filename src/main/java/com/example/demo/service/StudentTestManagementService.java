package com.example.demo.service;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.response.StartTestResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.InternalErrorMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.StudentTestRepository;
import com.example.demo.validation.StartTestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentTestManagementService {

    private final QuestionMapper questionMapper;
    private final StartTestValidator startTestValidator;
    private final StudentTestRepository studentTestRepository;

    private final StudentService studentService;
    private final TestService testService;
    private final QuestionService questionService;

    public StartTestResponse startTest(UUID studentId, UUID testId) {
        var testToStart = testService.getById(testId);
        var student = studentService.getStudentReferenceById(studentId);
        startTestValidator.validate(student, testToStart);

        create(TestProgressState.STARTED, student, testToStart);

        var firstQuestion = questionService.findByTestAndNumber(testToStart, 1)
                .orElseThrow(() -> new NotFoundException(InternalErrorMessage.TEST_QUESTION_NOT_FOUND_WITH_NUMBER));

        return StartTestResponse.builder()
                .testName(testToStart.getName())
                .firstQuestion(questionMapper.toQuestionResponse(firstQuestion, null))
                .build();
    }

    private StudentTest create(TestProgressState state, Student student, Test test) {
        var studentTest = StudentTest.builder()
                .state(state)
                .student(student)
                .test(test)
                .build();

        return studentTestRepository.save(studentTest);
    }
}
