package com.example.demo.exception;

import com.example.demo.exception.base.BaseException;
import com.example.demo.exception.message.BaseErrorMessage;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(BaseErrorMessage baseErrorMessage) {
        super(HttpStatus.BAD_REQUEST, baseErrorMessage);
    }
}
