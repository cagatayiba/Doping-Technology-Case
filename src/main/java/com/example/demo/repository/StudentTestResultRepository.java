package com.example.demo.repository;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentTestResult;
import com.example.demo.domain.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentTestResultRepository extends JpaRepository<StudentTestResult, UUID> {

    Optional<StudentTestResult> findByStudentAndTest(Student student, Test test);
}
