package com.example.demo.validation;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.model.TestState;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.repository.StudentTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerQuestionValidator extends AbstractValidator {

    private final StudentTestRepository studentTestRepository;

    public void validate(Student student, Test test){
        validateTestState(test);
        validateStudentTestState(student, test);
    }

    private void validateTestState(Test test){
        check(test.getState() == TestState.READY, ErrorMessage.INVALID_TEST_STATE_TO_ANSWER_QUESTION);
    }

    private void validateStudentTestState(Student student, Test test){
        var studentTest = studentTestRepository.findByStudentAndTest(student, test);
        throwIf(studentTest.isEmpty(), ErrorMessage.CANNOT_ANSWER_TEST_NOT_STARTED);
        assert studentTest.isPresent();
        check(studentTest.get().getState() == TestProgressState.SUBMITTED, ErrorMessage.CANNOT_ANSWER_TEST_SUBMITTED);
    }
}

