package com.example.demo.exception;

import com.example.demo.exception.base.BaseException;
import com.example.demo.exception.message.BaseErrorMessage;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(BaseErrorMessage baseErrorMessage) {
        super(HttpStatus.NOT_FOUND, baseErrorMessage);
    }

}
