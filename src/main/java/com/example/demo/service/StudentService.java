package com.example.demo.service;

import com.example.demo.domain.model.Student;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.message.InternalErrorMessage;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getReferenceById(UUID id) {
        return studentRepository.getReferenceById(id);
    }

    public Student getById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InternalErrorMessage.ITEM_NOT_FOUND));
    }
}
