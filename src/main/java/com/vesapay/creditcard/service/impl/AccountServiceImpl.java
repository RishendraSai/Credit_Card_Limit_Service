package com.vesapay.creditcard.service.impl;

import com.vesapay.creditcard.common.application.ApplicationConfig;
import com.vesapay.creditcard.common.enums.StatusType;
import com.vesapay.creditcard.common.exceptions.DataConflictException;
import com.vesapay.creditcard.common.exceptions.ValidationException;
import com.vesapay.creditcard.common.utils.AccountNumberGenerator;
import com.vesapay.creditcard.common.utils.FieldValidationStatus;
import com.vesapay.creditcard.data.entities.AccountEntity;
import com.vesapay.creditcard.data.entities.CustomersEntity;
import com.vesapay.creditcard.data.entities.mappers.AccountEntityMapper;
import com.vesapay.creditcard.data.entities.mappers.CustomerEntityMapper;
import com.vesapay.creditcard.data.repository.AccountRepository;
import com.vesapay.creditcard.data.repository.CustomerRepository;
import com.vesapay.creditcard.models.AddAccountDto;
import com.vesapay.creditcard.models.UserResponseDto;
import com.vesapay.creditcard.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final Logger logger = LogManager.getLogger(AccountServiceImpl.class);


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public UserResponseDto addAccount(AddAccountDto addAccountDto) {

        UserResponseDto userResponseDto = null;
        Optional<CustomersEntity> optionalCustomersEntity = customerRepository.findByPhoneNumber(addAccountDto.getPhone());
        if(!optionalCustomersEntity.isPresent()) {
            try {
                logger.info("Creating a New account for phoneNumber={}",addAccountDto.getPhone());
                CustomersEntity customersEntity = CustomerEntityMapper.INSTANCE.fromDto(addAccountDto, StatusType.ACTIVE.toString(),
                        new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
                String accountNumber = String.valueOf(AccountNumberGenerator.generator());
                AccountEntity accountEntity = AccountEntityMapper.INSTANCE.fromDto(accountNumber, applicationConfig.getAmountLimit(),
                        applicationConfig.getPerTransactionLimit(), applicationConfig.getLastPerTransactionLimit(), applicationConfig.getLastAmountLimit(),
                        new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), customersEntity);
                AccountEntity updatedAccountEntity = accountRepository.save(accountEntity);
                logger.info("Onboarded new Customer for the phoneNumber={} , accountId={}"
                        , accountEntity.getCustomersEntity().getPhone(),accountEntity.getAccountId());
                userResponseDto= AccountEntityMapper.INSTANCE.toDto(updatedAccountEntity);
            }
            catch(Exception e) {
                logger.info("Exception e={}",e.getMessage());
            }

        }
        else {
            logger.info("Phone number is Already Exists in the System");
            throw new DataConflictException(FieldValidationStatus.PHONE_NUMBER_ALREADY_EXISTS_CODE,FieldValidationStatus.PHONE_NUMBER_ALREADY_EXISTS_MSG);
        }
        return userResponseDto;

    }

    @Override
    public UserResponseDto getByAccountId(String accountId) {
        UserResponseDto userResponseDto = null;
        Optional<AccountEntity> accountEntityOptional = accountRepository.findByAccountId(accountId);
        if(accountEntityOptional.isPresent()){
            logger.info("Account is found with accountId={}",accountId);
            AccountEntity accountEntity = accountEntityOptional.get();
            userResponseDto= AccountEntityMapper.INSTANCE.toDto(accountEntity);

        }
        else{
            logger.info("AccountID is not Exists in System - accountId={}",accountId);
            throw new DataConflictException(FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_CODE,FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_MSG);

        }

        return userResponseDto;
    }
}
