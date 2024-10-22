package com.example.demo.repository;

import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Optional<Question> findByTestAndNumber(Test test, int number);
}
