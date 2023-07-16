package com.vesapay.creditcard.common.exceptions;

import com.vesapay.creditcard.api.models.ErrorDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends  RuntimeException{
    private String code;

    private String message;

    private List<ErrorDetails> errorDetails;

    public ValidationException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ValidationException(String code, String message, List<ErrorDetails> errorDetails) {
        super();
        this.code = code;
        this.message = message;
        this.errorDetails = errorDetails;
    }
}
