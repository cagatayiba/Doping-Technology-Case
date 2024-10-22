package com.example.demo.service;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.response.ContinueTestResponse;
import com.example.demo.domain.response.QuestionProgressResponse;
import com.example.demo.domain.response.QuestionProgressState;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.domain.response.StartTestResponse;
import com.example.demo.domain.response.TestResultDetailResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.StudentTestRepository;
import com.example.demo.validation.ContinueTestValidator;
import com.example.demo.validation.StartTestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final ContinueTestValidator continueTestValidator;
    private final TestResultService testResultService;

    public StartTestResponse startTest(UUID studentId, UUID testId) {
        var testToStart = testService.getById(testId);
        var student = studentService.getReferenceById(studentId);
        startTestValidator.validate(student, testToStart);

        create(student, testToStart);

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

    public ContinueTestResponse continueTest(UUID studentId, UUID testId) {
        var student = studentService.getReferenceById(studentId);
        var test = testService.getById(testId);
        var studentTest = getByStudentAndTest(student, test);
        continueTestValidator.validate(studentTest);

        test.sortQuestionsByNumber();
        var testQuestions = test.getQuestions();
        var studentAnswers = studentAnswerService.findByStudentAndQuestions(student, testQuestions);
        var studentAnswersByQuestion = studentAnswers.stream()
                .collect(Collectors.toMap(StudentAnswer::getQuestion, entity -> entity));

        var testProgress = testQuestions.stream()
                .map(question ->
                        QuestionProgressResponse.builder()
                                .questionNumber(question.getNumber())
                                .questionState(studentAnswersByQuestion.containsKey(question) ? QuestionProgressState.ANSWERED : QuestionProgressState.UNANSWERED)
                                .build()
                ).toList();
        var firstQuestion = testQuestions.getFirst();
        var firstQuestionAnswer = Optional.ofNullable(studentAnswersByQuestion.get(firstQuestion));
        return ContinueTestResponse.builder()
                .testName(test.getName())
                .testProgress(testProgress)
                .firstQuestion(questionMapper.toQuestionResponse(firstQuestion, firstQuestionAnswer.map(StudentAnswer::getId).orElse(null)))
                .build();
    }


    public TestResultDetailResponse submitTest(UUID studentId, UUID testId) {
        var student = studentService.getReferenceById(studentId);
        var test = testService.getById(testId);
        var studentTest = getByStudentAndTest(student, test);
        // TODO validation
        studentTest.setState(TestProgressState.SUBMITTED);
        studentTestRepository.save(studentTest);

        return testResultService.generateTestResult(test, student);
    }

    private StudentTest getByStudentAndTest(Student student, Test test) {
        return studentTestRepository.findByStudentAndTest(student, test)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));
    }

    private void create(Student student, Test test) {
        var studentTest = StudentTest.builder()
                .state(TestProgressState.STARTED)
                .student(student)
                .test(test)
                .build();

        studentTestRepository.save(studentTest);
    }
}
