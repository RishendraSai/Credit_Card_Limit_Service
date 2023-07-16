package com.vesapay.creditcard.common.utils;

import com.vesapay.creditcard.common.exceptions.ErrorDetails;

public class CommonUtil {

    public static ErrorDetails createErrorDetails(String errorCode, String field, String message) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setCode(String.valueOf(errorCode));
        errorDetail.setField(field);
        errorDetail.setMessage(message);
        return errorDetail;
    }

    public static ErrorDetails createErrorDetails(String errorCode, String message) {
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setCode(String.valueOf(errorCode));
        errorDetail.setMessage(message);
        return errorDetail;
    }
}
