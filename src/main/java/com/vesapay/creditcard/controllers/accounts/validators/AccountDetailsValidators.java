package com.vesapay.creditcard.controllers.accounts.validators;

import com.vesapay.creditcard.api.models.AddAccountRequest;
import com.vesapay.creditcard.common.exceptions.ErrorDetails;
import com.vesapay.creditcard.common.exceptions.ValidationException;
import com.vesapay.creditcard.common.utils.CommonUtil;
import com.vesapay.creditcard.common.utils.CreditCardResponseCodeType;
import com.vesapay.creditcard.common.utils.FieldValidationStatus;
import com.vesapay.creditcard.common.utils.RegExUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDetailsValidators {



    private static final Logger LOGGER = LogManager.getLogger(AccountDetailsValidators.class);

    public void AddAccountDetailsValidator(AddAccountRequest addAccountRequest){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(addAccountRequest == null){
            throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateName(addAccountRequest.getName()));
        errorDetails.addAll(validatePhoneNumber(addAccountRequest.getPhone()));

        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }

    public void AccountIdValidator(String accountId) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(accountId == null){
            throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateAccountId(accountId));

        if (errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }


    }

    private  List<ErrorDetails> validateName(String name) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(name)){
            if(!RegExUtil.isAlphaNumericWithSpecialChar(name)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_NAME_INVALID_CODE,
                        "name",FieldValidationStatus.USER_NAME_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_NAME_INVALID_CODE,
                    "name",FieldValidationStatus.USER_NAME_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }

    private   List<ErrorDetails> validatePhoneNumber(String number) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(number)){
            if(!RegExUtil.isNumeric(number) || number.length()!=10){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_PHONE_NUMBER_INVALID_CODE,
                        "phoneNumber",FieldValidationStatus.USER_PHONE_NUMBER_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.USER_PHONE_NUMBER_INVALID_CODE,
                    "phoneNumber",FieldValidationStatus.USER_PHONE_NUMBER_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;

    }


    private  List<ErrorDetails> validateAccountId(String number) {
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(number)){
            if(!RegExUtil.isNumeric(number)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.ACCOUNT_ID_INVALID_CODE,
                        "accountId",FieldValidationStatus.ACCOUNT_ID_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.ACCOUNT_ID_INVALID_CODE,
                    "accountId",FieldValidationStatus.ACCOUNT_ID_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;

    }

    private  void sendBadRequestResponse(List<ErrorDetails> errorDetails) {
        throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG,errorDetails);


    }


}
