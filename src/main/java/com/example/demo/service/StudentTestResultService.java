package com.example.demo.service;

import com.example.demo.domain.dto.TestResultDto;
import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.domain.model.StudentTestResult;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.response.QuestionResult;
import com.example.demo.domain.response.QuestionResultResponse;
import com.example.demo.domain.response.TestResultDetailResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.mapper.StudentTestResultMapper;
import com.example.demo.repository.StudentTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentTestResultService {

    private final StudentTestResultMapper studentTestResultMapper;

    private final StudentTestResultRepository studentTestResultRepository;

    public TestResultDetailResponse generateTestResult(Test test, List<Question> questions, List<StudentAnswer> answers) {
        var studentAnswersByQuestion = answers.stream()
                .collect(Collectors.toMap(StudentAnswer::getQuestion, entity -> entity));

        var questionResults = questions.stream()
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

        var testResult = TestResultDto.builder()
                .testId(test.getId())
                .testName(test.getName())
                .numberOfCorrectAnswers((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.CORRECT).count())
                .numberOfWrongAnswers((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.WRONG).count())
                .numberOfUnansweredQuestions((int) questionResults.stream().filter(questionResult -> questionResult.result() == QuestionResult.UNANSWERED).count())
                .build();

        save(studentTestResultMapper.toEntity(testResult));

        return new TestResultDetailResponse(testResult, questionResults);
    }

    public TestResultDto getTestResult(Student student, Test test){
        return studentTestResultMapper.toTestResultDto(getByStudentAndTest(student, test));
    }

    public StudentTestResult getByStudentAndTest(Student student, Test test) {
        return studentTestResultRepository.findByStudentAndTest(student, test)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));
    }

    private void save(StudentTestResult testResult) {
        studentTestResultRepository.save(testResult);
    }
}
