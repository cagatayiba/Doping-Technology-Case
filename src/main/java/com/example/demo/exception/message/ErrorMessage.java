package com.example.demo.exception.message;

import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {
    // start test
    CANNOT_START_TEST_NOT_READY("Bu test henüz hazır değil."),
    CANNOT_START_TEST_ALREADY_HAS_RECORD("Bu teste ait bir kaydınız zaten var."),

    // answer question
    INVALID_TEST_STATE_TO_ANSWER_QUESTION("Hazır olmayan bir teste dair sorular cevaplanamaz."),
    CANNOT_ANSWER_TEST_SUBMITTED("Bitirdiğiniz bir testin sorularını tekrar cevaplayamazsınız."),
    CANNOT_ANSWER_TEST_NOT_STARTED("Henüz başlamadığınız bir testin sorularını cevaplayamazsınız."),

    // continue test
    INVALID_TEST_PROGRESS_STATE_TO_CONTINUE("Bir teste devam edebilmek için başlamış olmanız gerekmektedir."),

    // generic
    ITEM_NOT_FOUND("Aradığınızı bulamadık."),
    UNSPECIFIED_ERROR_MESSAGE("Bir şeyler ters gitti. Lütfen daha sonra tekrar deneyin.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
