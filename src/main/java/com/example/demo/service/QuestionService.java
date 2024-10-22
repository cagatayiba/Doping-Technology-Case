package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Test;
import com.example.demo.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> findByTestAndNumber(Test test, int questionNumber) {
        return questionRepository.findByTestAndNumber(test, questionNumber);
    }

}
