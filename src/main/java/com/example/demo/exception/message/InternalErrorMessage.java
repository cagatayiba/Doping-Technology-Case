package com.example.demo.exception.message;

import lombok.Getter;

@Getter
public enum InternalErrorMessage implements BaseErrorMessage{
    ITEM_NOT_FOUND("Item not found with given id."),
    TEST_QUESTION_NOT_FOUND_WITH_NUMBER("The test does not contain question with given number.");

    private final String message;
    private final boolean isClientMessage;

    InternalErrorMessage(String message) {
        this.message = message;
        this.isClientMessage = false;
    }
}
