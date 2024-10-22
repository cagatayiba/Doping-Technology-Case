package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.InternalErrorMessage;
import com.example.demo.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question getReferenceById(UUID questionId) {
        return questionRepository.getReferenceById(questionId);
    }

    public Question getByTestAndNumber(Test test, int questionNumber) {
        return findByTestAndNumber(test, questionNumber)
                .orElseThrow(() -> new NotFoundException(InternalErrorMessage.TEST_QUESTION_NOT_FOUND_WITH_NUMBER));
    }

    public Optional<Question> findByTestAndNumber(Test test, int questionNumber) {
        return questionRepository.findByTestAndNumber(test, questionNumber);
    }

    public int getNumberOfQuestionInTest(Test test) {
        return questionRepository.countByTest(test);
    }
}
