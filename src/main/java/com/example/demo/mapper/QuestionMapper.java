package com.example.demo.mapper;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = DefaultMapperConfiguration.class)
public interface QuestionMapper{

    QuestionResponse toQuestionResponse(Question question, UUID chosenOptionId);
}
