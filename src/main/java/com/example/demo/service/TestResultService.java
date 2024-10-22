package com.example.demo.service;

import com.example.demo.domain.dto.TestResultDto;
import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.domain.model.TestResult;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.response.QuestionResult;
import com.example.demo.domain.response.QuestionResultResponse;
import com.example.demo.domain.response.TestResultDetailResponse;
import com.example.demo.domain.response.TestResultResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.mapper.TestResultMapper;
import com.example.demo.repository.StudentTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestResultService {

    private final TestResultMapper testResultMapper;

    private final StudentTestResultRepository studentTestResultRepository;
    private final StudentService studentService;
    private final TestService testService;
    private final StudentAnswerService studentAnswerService;

    public TestResultDetailResponse generateTestResult(Test test, Student student) {
        test.sortQuestionsByNumber();
        var testQuestions = test.getQuestions();
        var studentAnswers = studentAnswerService.findByStudentAndQuestions(student, testQuestions);
        var questionResults = getQuestionResultResponses(testQuestions, studentAnswers);

        var testResult = TestResultDto.builder()
                .testId(test.getId())
                .testName(test.getName())
                .numberOfCorrectAnswers((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.CORRECT).count())
                .numberOfWrongAnswers((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.WRONG).count())
                .numberOfUnansweredQuestions((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.UNANSWERED).count())
                .build();

        save(testResultMapper.toEntity(testResult));

        return new TestResultDetailResponse(testResult, questionResults);
    }

    public TestResultResponse getTestResults(UUID studentId, Pageable pageable) {
        var student = studentService.getReferenceById(studentId);
        var results = studentTestResultRepository.findByStudent(student, pageable).stream()
                .map(testResultMapper::toTestResultDto)
                .toList();
        return new TestResultResponse(results);
    }

    public TestResultDetailResponse getTestResultDetail(UUID studentId, UUID testId){
        var student = studentService.getReferenceById(studentId);
        var test = testService.getById(testId);

        var testResultMetadata =  testResultMapper.toTestResultDto(getByStudentAndTest(student, test));

        test.sortQuestionsByNumber();
        var testQuestions = test.getQuestions();
        var studentAnswers = studentAnswerService.findByStudentAndQuestions(student, testQuestions);
        var questionResults = getQuestionResultResponses(testQuestions, studentAnswers);

        return new TestResultDetailResponse(testResultMetadata, questionResults);
    }

    public TestResult getByStudentAndTest(Student student, Test test) {
        return studentTestResultRepository.findByStudentAndTest(student, test)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));
    }

    private void save(TestResult testResult) {
        studentTestResultRepository.save(testResult);
    }

    private static List<QuestionResultResponse> getQuestionResultResponses(List<Question> questions, List<StudentAnswer> answers) {
        var studentAnswersByQuestion = answers.stream()
                .collect(Collectors.toMap(StudentAnswer::getQuestion, entity -> entity));

        return questions.stream()
                .map(question -> {
                    var questionResult = QuestionResult.UNANSWERED;
                    var matchedAnswer = studentAnswersByQuestion.get(question);
                    if(matchedAnswer != null) {
                        questionResult = matchedAnswer.getAnswerOption().getIsCorrectOption() ? QuestionResult.CORRECT : QuestionResult.WRONG;
                    }
                    return QuestionResultResponse.builder()
                            .questionId(question.getId())
                            .questionNumber(question.getNumber())
                            .result(questionResult)
                            .build();
                }).toList();
    }
}
