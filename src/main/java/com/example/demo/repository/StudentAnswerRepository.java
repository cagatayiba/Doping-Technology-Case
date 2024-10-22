package com.example.demo.repository;

import com.example.demo.domain.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, UUID> {
}
