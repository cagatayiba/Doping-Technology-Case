package com.example.demo.mapper;

import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestState;
import com.example.demo.domain.request.CreateTestRequest;
import com.example.demo.domain.response.TestResponse;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = DefaultMapperConfiguration.class)
public interface TestMapper {

    TestResponse toTestResponse(Test test);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", source = ".", qualifiedByName = {"mapState"})
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Test toEntity(CreateTestRequest createTestRequest);

    @Named("mapState")
    default TestState mapState(CreateTestRequest createTestRequest){
        return TestState.DRAFT;
    }
}
