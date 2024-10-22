package com.example.demo.repository;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentTestResultRepository extends JpaRepository<TestResult, UUID> {

    Optional<TestResult> findByStudentAndTest(Student student, Test test);

    Page<TestResult> findByStudent(Student test, Pageable pageable);

    List<TestResult> findByStudent(Student test);
}
