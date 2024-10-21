package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Test {

    @GetMapping("greetings")
    public String greetings() {
        return "Hello From The App!";
    }
}
