package com.example.demo.exception;

import com.example.demo.exception.base.BaseException;
import com.example.demo.exception.message.BaseErrorMessage;
import org.springframework.http.HttpStatus;

public class InternalServerException extends BaseException {

    public InternalServerException(BaseErrorMessage baseErrorMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, baseErrorMessage);
    }
}
