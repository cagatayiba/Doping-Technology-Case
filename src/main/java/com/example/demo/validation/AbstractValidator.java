package com.example.demo.validation;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.message.BaseErrorMessage;

public class AbstractValidator {

    protected void check(boolean condition, BaseErrorMessage message) {
        if (!condition) throw new BadRequestException(message);;

    }

    protected void throwIf(boolean condition, BaseErrorMessage message) {
        if (condition) throw new BadRequestException(message);;
    }
}
