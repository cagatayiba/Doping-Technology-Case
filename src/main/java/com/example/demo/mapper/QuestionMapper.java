package com.example.demo.mapper;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.request.AddQuestionRequest;
import com.example.demo.domain.response.QuestionAdminResponse;
import com.example.demo.domain.response.QuestionResponse;
import com.example.demo.mapper.config.DefaultMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = DefaultMapperConfiguration.class, uses = {AnswerOptionMapper.class})
public interface QuestionMapper{

    QuestionResponse toQuestionResponse(Question question, UUID chosenOptionId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "test", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Question toEntity(AddQuestionRequest addQuestionRequest);

    QuestionAdminResponse toQuestionAdminResponse(Question question);
}
