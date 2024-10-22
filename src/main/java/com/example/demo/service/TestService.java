package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Test;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.InternalErrorMessage;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public Test getById(UUID testId){
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(InternalErrorMessage.ITEM_NOT_FOUND));
    }
}
