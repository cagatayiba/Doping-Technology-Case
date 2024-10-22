package com.example.demo.repository;

import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {

    @Modifying
    @Query("UPDATE Test t SET t.state = :state WHERE t.id = :id")
    void updateState(@Param("id") UUID id, @Param("state") TestState state);
}
