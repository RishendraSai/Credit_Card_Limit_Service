package com.vesapay.creditcard.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OfferLimitResponseDto {

    private String accountId;
    private String limitType;
    private String newLimit;
    private String offerId;
    private String status;
    private Timestamp offerActivationTime;
    private Timestamp offerExpiryTime;
}
