package com.example.demo.mapper;

import com.example.demo.domain.model.AnswerOption;
import com.example.demo.domain.response.AnswerOptionResponse;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfiguration.class)
public interface AnswerOptionMapper {

    AnswerOptionResponse toAnswerOptionResponse(AnswerOption answerOption);
}
