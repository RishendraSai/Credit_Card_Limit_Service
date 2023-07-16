package com.vesapay.creditcard.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CreditCardExceptionsHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException validationException){
        CreditException creditCardExceptions = new CreditException(
                validationException.getCode(),
                validationException.getMessage(),
                validationException.getErrorDetails());
        return new ResponseEntity<>(creditCardExceptions, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(value = {DataConflictException.class})
    public ResponseEntity<Object> handleDataConflictException(DataConflictException dataConflictException){
        CreditException creditCardExceptions = new CreditException(
                dataConflictException.getCode(),
                dataConflictException.getMessage());
        return new ResponseEntity<>(creditCardExceptions, HttpStatus.OK);

    }
}
