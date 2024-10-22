package com.example.demo.service;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.repository.StudentAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;

    public Optional<StudentAnswer> findByStudentAndQuestion(Student student, Question question) {
        return studentAnswerRepository.findByStudentAndQuestion(student, question);
    }
}
