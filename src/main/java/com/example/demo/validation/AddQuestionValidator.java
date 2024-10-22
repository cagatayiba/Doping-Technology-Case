package com.example.demo.validation;

import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestState;
import com.example.demo.domain.request.AddAnswerOptionRequest;
import com.example.demo.domain.request.AddQuestionRequest;
import com.example.demo.exception.message.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddQuestionValidator extends AbstractValidator {

    public void validate(Test test, AddQuestionRequest addQuestionRequest) {
        validateTestState(test);
        validateAnswerOptions(addQuestionRequest.answerOptions());
    }

    private void validateTestState(Test test){
        check(test.getState() == TestState.DRAFT, ErrorMessage.INVALID_TEST_STATE_TO_ADD_QUESTION);

    }

    private void validateAnswerOptions(List<AddAnswerOptionRequest> answerOptions){
        check(answerOptions.stream().filter(AddAnswerOptionRequest::isCorrectOption).count() == 1, ErrorMessage.OPTIONS_MUST_HAVE_ONE_CORRECT_ANSWER);
    }
}
