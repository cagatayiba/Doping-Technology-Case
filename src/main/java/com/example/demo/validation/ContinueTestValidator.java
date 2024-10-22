package com.example.demo.validation;

import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.exception.message.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class ContinueTestValidator extends AbstractValidator{

    public void validate(StudentTest studentTest) {
        validateTestState(studentTest);
    }

    private void validateTestState(StudentTest studentTest){
        check(studentTest.getState() == TestProgressState.STARTED, ErrorMessage.INVALID_TEST_PROGRESS_STATE_TO_CONTINUE);
    }
}
