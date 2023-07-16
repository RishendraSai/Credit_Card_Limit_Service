package com.vesapay.creditcard.models;


import lombok.Data;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
public class OfferLimitDto {

    private String accountId;
    private String limitType;
    private String newLimit;
    private Timestamp offerActivationTime;
    private Timestamp offerExpiryTime;
}
