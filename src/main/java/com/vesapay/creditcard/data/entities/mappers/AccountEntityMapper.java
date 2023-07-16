package com.vesapay.creditcard.data.entities.mappers;

import com.vesapay.creditcard.data.entities.AccountEntity;
import com.vesapay.creditcard.data.entities.CustomersEntity;
import com.vesapay.creditcard.models.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

@Mapper
public interface AccountEntityMapper {

    AccountEntityMapper INSTANCE = Mappers.getMapper(AccountEntityMapper.class);



    @Mapping(source = "customersEntity", target = "customersEntity")
    AccountEntity fromDto(String accountId, Long amountLimit, Long perTransactionLimit , Long lastAmountLimit, Long lastPerTransactionLimit,
                          Timestamp accountLimitUpdateTime, Timestamp perTransactionLimitUpdateTime, CustomersEntity customersEntity);



    @Mapping(source = "accountEntity.perTransactionLimit", target = "account.perTransactionLimit")
    @Mapping(source = "accountEntity.amountLimit", target = "account.amountLimit")
    @Mapping(source = "accountEntity.accountId", target = "account.accountId")
    @Mapping(source = "accountEntity.customersEntity.status", target = "status")
    @Mapping(source = "accountEntity.customersEntity.phone", target = "phone")
    @Mapping(source = "accountEntity.customersEntity.name", target = "name")
    UserResponseDto toDto(AccountEntity accountEntity);
}
