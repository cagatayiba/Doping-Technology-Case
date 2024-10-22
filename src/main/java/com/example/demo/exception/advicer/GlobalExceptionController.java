package com.example.demo.exception.advicer;

import com.example.demo.domain.response.error.ErrorResponse;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.base.BaseException;
import com.example.demo.exception.message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    private final BaseException genericClientException = new InternalServerException(ErrorMessage.UNSPECIFIED_ERROR_MESSAGE);


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        return getGenericErrorResponse(ex, request);
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(BaseException ex, WebRequest request) {
        return getErrorResponse(ex, request);
    }

    private ResponseEntity<ErrorResponse> getGenericErrorResponse(Exception ex, WebRequest request) {
        logger.error("An internal exception is occurred: ", ex);
        return getErrorResponse(genericClientException, request);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(BaseException ex, WebRequest request) {
        var errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .message(ex.getBaseErrorMessage().getMessage())
                .description(request.getDescription(false))
                .httpCode(ex.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), ex.getHttpStatus());
    }
}


