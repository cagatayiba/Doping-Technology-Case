package com.example.demo.service;

import com.example.demo.domain.model.*;
import com.example.demo.domain.response.*;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.StudentTestRepository;
import com.example.demo.validation.ContinueTestValidator;
import com.example.demo.validation.StartTestValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentTestManagementServiceTest {

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private StartTestValidator startTestValidator;

    @Mock
    private StudentTestRepository studentTestRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private TestService testService;

    @Mock
    private QuestionService questionService;

    @Mock
    private StudentAnswerService studentAnswerService;

    @Mock
    private ContinueTestValidator continueTestValidator;

    @Mock
    private TestResultService testResultService;

    @InjectMocks
    private StudentTestManagementService studentTestManagementService;

    @org.junit.jupiter.api.Test
    public void start_test_should_create_student_test_record() {
        // Given
        var studentId = UUID.randomUUID();
        var testId = UUID.randomUUID();
        var test = new Test();
        test.setId(testId);
        test.setName("math test");
        var student = Mockito.mock(Student.class);

        var studentTest = StudentTest.builder()
                .state(TestProgressState.STARTED)
                .student(student)
                .test(test)
                .build();

        var firstQuestion = Mockito.mock(Question.class);

        when(testService.getById(testId)).thenReturn(test);
        when(studentService.getReferenceById(studentId)).thenReturn(student);
        when(studentTestRepository.save(studentTest)).thenReturn(Mockito.mock(StudentTest.class));
        when(questionService.getByTestAndNumber(test, 1)).thenReturn(firstQuestion);
        when(questionMapper.toQuestionResponse(firstQuestion, null)).thenReturn(Mockito.mock(QuestionResponse.class));

        // When
        studentTestManagementService.startTest(studentId, testId);

        // Then
        verify(startTestValidator).validate(student, test);
        verify(studentTestRepository).save(studentTest);
        verify(questionService).getByTestAndNumber(test, 1);
        verify(firstQuestion).sortAnswerOptions();
        verify(questionMapper).toQuestionResponse(firstQuestion, null);
    }

    @org.junit.jupiter.api.Test
    public void get_question_should_return_question_response() {
        // Given
        var studentId = UUID.randomUUID();
        var testId = UUID.randomUUID();
        int questionNumber = 1;
        var test = new Test();
        test.setId(testId);
        test.setName("math test");
        var student = Mockito.mock(Student.class);

        var question = new Question();

        var chosenOptionId = UUID.randomUUID();
        var answerOption = new AnswerOption();
        answerOption.setId(chosenOptionId);
        var answer = new StudentAnswer();
        answer.setAnswerOption(answerOption);
        var answerResponse = Mockito.mock(AnswerOptionResponse.class);

        var expected = new QuestionResponse(question.getId(), questionNumber, question.getContent(), chosenOptionId, List.of(answerResponse));

        when(testService.getReferenceById(testId)).thenReturn(test);
        when(studentService.getReferenceById(studentId)).thenReturn(student);
        when(questionService.getByTestAndNumber(test, questionNumber)).thenReturn(question);
        when(studentAnswerService.findByStudentAndQuestion(student, question)).thenReturn(Optional.of(answer));
        when(questionMapper.toQuestionResponse(question, chosenOptionId)).thenReturn(expected);

        // When
        var actual = studentTestManagementService.getQuestion(studentId, testId, questionNumber);

        // Then
        assertEquals(expected, actual);

        verify(testService).getReferenceById(testId);
        verify(studentService).getReferenceById(studentId);
        verify(questionService).getByTestAndNumber(test, questionNumber);
        verify(studentAnswerService).findByStudentAndQuestion(student, question);
        verify(questionMapper).toQuestionResponse(question,chosenOptionId);
    }

    @org.junit.jupiter.api.Test
    public void continue_test_should_create_student_test_response() {
        // Given
        var student = new Student();
        student.setId(UUID.randomUUID());

        int questionNumber = 1;
        var question = new Question();
        question.setNumber(questionNumber);

        var test = new Test();
        test.setId(UUID.randomUUID());
        test.setQuestions(new ArrayList<>(List.of(question)));
        var testQuestions = test.getQuestions();

        var studentTest = StudentTest.builder()
                .student(student)
                .test(test)
                .build();

        var studentAnswer = new StudentAnswer();
        studentAnswer.setQuestion(question);
        studentAnswer.setStudent(student);

        var studentAnswers = List.of(studentAnswer);

        var studentAnswerByQuestion = Map.of(question, studentAnswer);

        var testProgress = List.of(QuestionProgressResponse.builder()
                .questionNumber(question.getNumber())
                .questionState(QuestionProgressState.ANSWERED)
                .build());

        var firstQuestion = testQuestions.getFirst();
        var firstQuestionAnswer = Optional.of(studentAnswerByQuestion.get(firstQuestion));

        var questionResponse = Mockito.mock(QuestionResponse.class);

        var expected = ContinueTestResponse.builder()
                .testName(test.getName())
                .testProgress(testProgress)
                .firstQuestion(questionResponse)
                .build();

        when(studentService.getReferenceById(student.getId())).thenReturn(student);
        when(testService.getById(test.getId())).thenReturn(test);
        when(studentAnswerService.findByStudentAndQuestions(student, testQuestions)).thenReturn(studentAnswers);
        when(questionMapper.toQuestionResponse(firstQuestion, firstQuestionAnswer.get().getId())).thenReturn(questionResponse);
        when(studentTestRepository.findByStudentAndTest(student, test)).thenReturn(Optional.of(studentTest));

        // When
        var actual = studentTestManagementService.continueTest(student.getId(), test.getId());

        // Then
        assertEquals(expected, actual);
        verify(studentService).getReferenceById(student.getId());
        verify(testService).getById(test.getId());
        verify(continueTestValidator).validate(studentTest);
        verify(studentAnswerService).findByStudentAndQuestions(student, testQuestions);
        verify(questionMapper).toQuestionResponse(firstQuestion, firstQuestionAnswer.get().getId());
    }

    @org.junit.jupiter.api.Test
    public void submit_test_should_return_test_result_detail_response() {
        // Given

        var studentId = UUID.randomUUID();
        var testId = UUID.randomUUID();
        var test = new Test();
        test.setId(testId);
        var student = Mockito.mock(Student.class);

        var studentTest = StudentTest.builder()
                .student(student)
                .test(test)
                .build();

        var expected = Mockito.mock(TestResultDetailResponse.class);

        when(testService.getById(testId)).thenReturn(test);
        when(studentService.getReferenceById(studentId)).thenReturn(student);
        when(testResultService.generateTestResult(test, student)).thenReturn(expected);
        when(studentTestRepository.findByStudentAndTest(student, test)).thenReturn(Optional.of(studentTest));

        // When
        var actual = studentTestManagementService.submitTest(studentId, testId);

        // Then
        assertEquals(expected, actual);

        verify(testResultService).generateTestResult(test, student);
        verify(studentService).getReferenceById(studentId);
        verify(studentTestRepository).save(studentTest);
        verify(testService).getById(testId);
        verify(studentTestRepository).findByStudentAndTest(student, test);
    }

}