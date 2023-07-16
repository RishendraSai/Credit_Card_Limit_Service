package com.vesapay.creditcard.common.exceptions;

import com.vesapay.creditcard.api.models.ErrorDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class CreditException {

    private final String message;

    private final String Code;

    private  List<ErrorDetails> errorDetails;

    public CreditException(String message, String code) {
        this.message = message;
        Code = code;

    }
    public CreditException(String message, String code, List<ErrorDetails> errorDetails) {
        this.message = message;
        Code = code;
        this.errorDetails = errorDetails;
    }
}
