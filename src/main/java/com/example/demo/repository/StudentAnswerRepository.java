package com.example.demo.repository;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, UUID> {

    Optional<StudentAnswer> findByStudentAndQuestion(Student student, Question question);

    List<StudentAnswer> findByStudentAndQuestionIn(Student student, List<Question> question);
}
