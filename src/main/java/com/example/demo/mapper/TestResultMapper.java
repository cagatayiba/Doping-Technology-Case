package com.example.demo.mapper;

import com.example.demo.domain.dto.TestResultDto;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestResult;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapperConfiguration.class)
public interface TestResultMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    TestResult toEntity(TestResultDto testResultDto, Student student, Test test);

    @Mapping(source = "test.id", target = "testId")
    @Mapping(source = "test.name", target = "testName")
    TestResultDto toTestResultDto(TestResult testResult);
}
