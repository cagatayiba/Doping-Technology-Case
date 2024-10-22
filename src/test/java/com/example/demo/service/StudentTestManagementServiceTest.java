package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.repository.StudentTestRepository;
import com.example.demo.validation.ContinueTestValidator;
import com.example.demo.validation.StartTestValidator;
import com.example.demo.domain.model.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
