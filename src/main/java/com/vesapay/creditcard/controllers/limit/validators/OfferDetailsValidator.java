package com.vesapay.creditcard.controllers.limit.validators;

import com.vesapay.creditcard.api.models.AddOfferRequest;
import com.vesapay.creditcard.api.models.UpdateOfferRequest;
import com.vesapay.creditcard.common.enums.LimitType;
import com.vesapay.creditcard.common.enums.StatusType;
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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OfferDetailsValidator {

    private static final Logger LOGGER = LogManager.getLogger(OfferDetailsValidator.class);


    public void AddOfferRequestValidator(AddOfferRequest addOfferRequest){

        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(addOfferRequest == null){
            throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateAccountId(addOfferRequest.getAccountId()));
        errorDetails.addAll(validateLimitType(addOfferRequest.getLimitType()));
        errorDetails.addAll(validateLimit(addOfferRequest.getNewLimit()));
        errorDetails.addAll(validateDateField(addOfferRequest.getOfferActivationTime()));
        errorDetails.addAll(validateDateField(addOfferRequest.getOfferExpiryTime()));

        if(errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }
    }

    public void getOfferDetailsValidator(String accountId){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(accountId==null){
            throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG);

        }
        errorDetails.addAll(validateAccountId(accountId));

        if(errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);
        }

    }

    public void updateOfferDetailsValidator(String OfferId, UpdateOfferRequest updateOfferRequest){
        List<ErrorDetails> errorDetails = new ArrayList<>();

        if(updateOfferRequest == null){
            throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG);
        }
        errorDetails.addAll(validateAccountId(OfferId));
        errorDetails.addAll(validateUpdateStatus(updateOfferRequest.getStatus()));

        if(errorDetails.size()>0){
            sendBadRequestResponse(errorDetails);

        }

    }


    private List<ErrorDetails> validateUpdateStatus(String status){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(status)){
            if(!status.equals(StatusType.ACCEPTED.toString()) &&
                    !status.equals(StatusType.REJECTED.toString()) ){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.STATUS_TYPE_INVALID_CODE,
                        "status",FieldValidationStatus.STATUS_TYPE_INVALID_MSG);
                errorDetails.add(errorDetail);
            }

        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.LIMIT_TYPE_INVALID_CODE,
                    "limitType",FieldValidationStatus.LIMIT_TYPE_INVALID_MSG);
            errorDetails.add(errorDetail);

        }
        return errorDetails;
    }



    private List<ErrorDetails> validateLimitType(String limitType){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(limitType)){
            if(!limitType.equals(LimitType.AMOUNT_LIMIT.toString()) &&
                    !limitType.equals(LimitType.PER_TRANSACTION_LIMIT.toString()) ){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.LIMIT_TYPE_INVALID_CODE,
                        "limitType",FieldValidationStatus.LIMIT_TYPE_INVALID_MSG);
                errorDetails.add(errorDetail);
            }

        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.LIMIT_TYPE_INVALID_CODE,
                    "limitType",FieldValidationStatus.LIMIT_TYPE_INVALID_MSG);
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

    private List<ErrorDetails> validateLimit(String newLimit){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(StringUtils.hasText(newLimit)){
            if(!RegExUtil.isNumeric(newLimit)){
                ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.AMOUNT_LIMIT_TYPE_INVALID_CODE,
                        "Limit",FieldValidationStatus.AMOUNT_LIMIT_TYPE_INVALID_MSG);
                errorDetails.add(errorDetail);
            }
        }
        else {
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.AMOUNT_LIMIT_TYPE_INVALID_CODE,
                    "Limit",FieldValidationStatus.AMOUNT_LIMIT_TYPE_INVALID_MSG);
            errorDetails.add(errorDetail);
        }
        return errorDetails;
    }
    private List<ErrorDetails> validateDateField(OffsetDateTime dateTime){
        List<ErrorDetails> errorDetails = new ArrayList<>();
        if(dateTime== null){
            ErrorDetails errorDetail = CommonUtil.createErrorDetails(FieldValidationStatus.DATE_ERROR_CODE,
                    "DateTime",FieldValidationStatus.DATE_ERROR_CODE_MSG);
            errorDetails.add(errorDetail);

        }
        return errorDetails;

    }


    private  void sendBadRequestResponse(List<ErrorDetails> errorDetails) {
        throw new ValidationException(CreditCardResponseCodeType.BAD_REQUEST_CODE,CreditCardResponseCodeType.BAD_REQUEST_MSG,errorDetails);


    }


}
