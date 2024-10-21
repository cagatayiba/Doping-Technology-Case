package com.example.demo.repository;

import com.example.demo.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {
}
