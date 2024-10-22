package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.request.AddQuestionRequest;
import com.example.demo.domain.response.AddQuestionResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.ErrorMessage;
import com.example.demo.mapper.AnswerOptionMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.TestMapper;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.validation.AddQuestionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestService testService;
    private final AddQuestionValidator addQuestionValidator;
    private final QuestionMapper questionMapper;
    private final AnswerOptionMapper answerOptionMapper;
    private final TestMapper testMapper;

    public Question getReferenceById(UUID questionId) {
        return questionRepository.getReferenceById(questionId);
    }

    @Cacheable(value = "questions", key = "#test.id + '-' + #questionNumber")
    public Question getByTestAndNumber(Test test, int questionNumber) {
        return findByTestAndNumber(test, questionNumber)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ITEM_NOT_FOUND));
    }

    public Optional<Question> findByTestAndNumber(Test test, int questionNumber) {
        return questionRepository.findByTestAndNumber(test, questionNumber);
    }

    public int getNumberOfQuestionInTest(Test test) {
        return questionRepository.countByTest(test);
    }

    @Transactional
    public AddQuestionResponse addQuestion(UUID testId, AddQuestionRequest addQuestionRequest){
        var test = testService.getById(testId);
        addQuestionValidator.validate(test, addQuestionRequest);

        var question = questionMapper.toEntity(addQuestionRequest);
        var answerOptions = addQuestionRequest.answerOptions().stream()
                .map(answerOptionMapper::toEntity)
                .toList();
        test.addQuestion(question.addAnswerOptions(answerOptions));

        questionRepository.save(question);

        return AddQuestionResponse.builder()
                .test(testMapper.toTestResponse(test))
                .questions(test.getQuestions().stream().map(questionMapper::toQuestionAdminResponse).toList())
                .build();
    }
}
