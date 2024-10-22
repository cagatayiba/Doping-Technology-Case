package com.example.demo.service;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.domain.response.StartTestResponse;
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
    private final StudentAnswerService studentAnswerService;

    public StartTestResponse startTest(UUID studentId, UUID testId) {
        var testToStart = testService.getById(testId);
        var student = studentService.getReferenceById(studentId);
        startTestValidator.validate(student, testToStart);

        create(TestProgressState.STARTED, student, testToStart);

        var numberOfQuestions = questionService.getNumberOfQuestionInTest(testToStart);
        var firstQuestion = questionService.getByTestAndNumber(testToStart, 1);
        firstQuestion.sortAnswerOptions();
        return StartTestResponse.builder()
                .testName(testToStart.getName())
                .numberOfQuestions(numberOfQuestions)
                .firstQuestion(questionMapper.toQuestionResponse(firstQuestion, null))
                .build();
    }

    public QuestionResponse getQuestion(UUID studentId, UUID testId, int questionNumber) {
        var test = testService.getReferenceById(testId);
        var student = studentService.getReferenceById(studentId);

        var question = questionService.getByTestAndNumber(test, questionNumber);
        var chosenOptionId = studentAnswerService.findByStudentAndQuestion(student, question)
                .map(studentAnswer -> studentAnswer.getAnswerOption().getId())
                .orElse(null);

        return questionMapper.toQuestionResponse(question, chosenOptionId);
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
