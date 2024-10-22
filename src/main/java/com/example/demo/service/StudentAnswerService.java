package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.domain.request.AnswerQuestionRequest;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.validation.AnswerQuestionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentAnswerService {

    private final AnswerQuestionValidator answerQuestionValidator;
    private final StudentAnswerRepository studentAnswerRepository;

    private final QuestionService questionService;
    private final StudentService studentService;
    private final AnswerOptionService answerOptionService;
    private final TestService testService;

    @Transactional
    public void answerQuestion(UUID studentId, UUID testId, AnswerQuestionRequest answerQuestionRequest) {
        var student = studentService.getReferenceById(studentId);
        var test = testService.getReferenceById(testId);
        answerQuestionValidator.validate(student, test);

        var question = questionService.getReferenceById(answerQuestionRequest.questionId());
        var chosenOption = answerOptionService.getReferenceById(answerQuestionRequest.chosenOption());

        var previousAnswer = findByStudentAndQuestion(student, question);
        if (previousAnswer.isPresent()) {
            // meaning student answered this question before and now he/she is changing his/her choice
            previousAnswer.get().setAnswerOption(chosenOption);
            return;
        }

        var newAnswer = new StudentAnswer(student, chosenOption, question);
        studentAnswerRepository.save(newAnswer);
    }

    public Optional<StudentAnswer> findByStudentAndQuestion(Student student, Question question) {
        return studentAnswerRepository.findByStudentAndQuestion(student, question);
    }

    public List<StudentAnswer> findByStudentAndQuestions(Student student, List<Question> questions) {
        return studentAnswerRepository.findByStudentAndQuestionIn(student, questions);
    }
}
