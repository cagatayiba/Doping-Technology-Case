package com.example.demo.mapper;

import com.example.demo.domain.dto.TestResultDto;
import com.example.demo.domain.model.TestResult;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapperConfiguration.class)
public interface TestResultMapper {
    TestResult toEntity(TestResultDto testResultDto);

    @Mapping(source = "test.id", target = "testId")
    @Mapping(source = "test.name", target = "testName")
    TestResultDto toTestResultDto(TestResult testResult);
}
