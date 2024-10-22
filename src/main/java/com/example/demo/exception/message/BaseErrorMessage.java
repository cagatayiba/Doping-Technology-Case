package com.example.demo.exception.message;

import java.io.Serializable;

@FunctionalInterface
public interface BaseErrorMessage extends Serializable {

    String getMessage();
}
