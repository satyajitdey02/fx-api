package com.example.fx.exception;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), "error");
        log.error("Error:", ex);

        return ResponseEntity.internalServerError().body(exceptionResponse);
    }

    @ExceptionHandler(TransactionException.class)
    public final ResponseEntity<Object> handleTransactionException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false), "error");
        log.error("Error: ", ex);

        return ResponseEntity.internalServerError().body(exceptionResponse);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        log.error("Error: ", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidTransactionDataException.class)
    public final ResponseEntity<Object> handleInvalidTransactionException(Exception ex, WebRequest request) {
        log.error("Error: ", ex);
        return ResponseEntity.badRequest().build();
    }
}
