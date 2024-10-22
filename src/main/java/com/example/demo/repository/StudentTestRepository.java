package com.example.demo.repository;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentTestRepository extends JpaRepository<StudentTest, UUID> {

    boolean existsByStudentAndTest(Student student, Test test);

    Optional<StudentTest> findByStudentAndTest(Student student, Test test);

    boolean existsByStudentAndTestAndState(Student student, Test test, TestProgressState state);
}
