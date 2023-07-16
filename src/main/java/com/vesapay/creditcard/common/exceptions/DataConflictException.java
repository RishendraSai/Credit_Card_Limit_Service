package com.vesapay.creditcard.common.exceptions;

import com.vesapay.creditcard.api.models.ErrorDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class DataConflictException extends  RuntimeException{
    private String code;

    private String message;

    public DataConflictException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}

