package com.example.demo.service;

import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestState;
import com.example.demo.domain.request.CreateTestRequest;
import com.example.demo.domain.response.TestResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.mapper.TestMapper;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestMapper testMapper;
    private final TestRepository testRepository;

    public TestResponse createTest(CreateTestRequest createTestRequest) {
        var savedEntity = testRepository.save(testMapper.toEntity(createTestRequest));
        return testMapper.toTestResponse(savedEntity);
    }

    @Transactional
    public void setReady(UUID testId){
        testRepository.updateState(testId, TestState.READY);
    }

    public Test getReferenceById(UUID id) {
        return testRepository.getReferenceById(id);
    }

    public Test getById(UUID testId){
        return testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));
    }
}
