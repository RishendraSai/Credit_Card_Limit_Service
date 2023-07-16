package com.vesapay.creditcard.common.application;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {

    @Value("${amount.limit}")
    private long amountLimit;

    @Value("${per.transaction.amount.limit}")
    private long perTransactionLimit;

    @Value("${last.amount.limit}")
    private long lastAmountLimit;

    @Value("${last.per.transaction.amount.limit}")
    private long lastPerTransactionLimit;
}
