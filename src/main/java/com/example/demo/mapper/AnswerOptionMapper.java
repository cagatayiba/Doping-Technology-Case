package com.example.demo.mapper;

import com.example.demo.domain.model.AnswerOption;
import com.example.demo.domain.request.AddAnswerOptionRequest;
import com.example.demo.domain.response.AnswerOptionResponse;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapperConfiguration.class)
public interface AnswerOptionMapper {

    AnswerOptionResponse toAnswerOptionResponse(AnswerOption answerOption);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    AnswerOption toEntity(AddAnswerOptionRequest addAnswerOptionRequest);
}
