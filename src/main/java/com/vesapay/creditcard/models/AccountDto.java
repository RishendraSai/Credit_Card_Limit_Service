package com.vesapay.creditcard.models;

import lombok.Data;

@Data
public class AccountDto {

    private long accountId;
    private long amountLimit;
    private long perTransactionLimit;

}
