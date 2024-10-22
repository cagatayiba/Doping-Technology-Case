package com.example.demo.validation;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestState;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.repository.StudentTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartTestValidator extends AbstractValidator {

    private final StudentTestRepository studentTestRepository;

    public void validate(Student student, Test test){
        validateTestState(test);
        validateNotStartedBefore(student, test);
    }

    private void validateTestState(Test test) {
        check(test.getState() == TestState.READY, ErrorMessage.CANNOT_START_TEST_NOT_READY);
    }

    private void validateNotStartedBefore(Student student, Test test){
        check(!studentTestRepository.existsByStudentAndTest(student, test), ErrorMessage.CANNOT_START_TEST_ALREADY_HAS_RECORD);
    }
}
