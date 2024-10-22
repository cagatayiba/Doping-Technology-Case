package com.example.demo.service;

import com.example.demo.domain.model.AnswerOption;
import com.example.demo.repository.AnswerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;

    public AnswerOption getReferenceById(UUID id) {
        return answerOptionRepository.getReferenceById(id);
    }
}
