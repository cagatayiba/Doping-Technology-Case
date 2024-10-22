package com.example.demo.exception;

import com.example.demo.exception.message.BaseErrorMessage;

public class IllegalArgumentException extends InternalServerException {
    public IllegalArgumentException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}
