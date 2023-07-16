package com.vesapay.creditcard.controllers.accounts;

import com.vesapay.creditcard.api.AccountsApi;
import com.vesapay.creditcard.api.models.AddAccountRequest;
import com.vesapay.creditcard.api.models.UserResponse;
import com.vesapay.creditcard.common.exceptions.ValidationException;
import com.vesapay.creditcard.controllers.accounts.mappers.AddAccountMapper;
import com.vesapay.creditcard.controllers.accounts.validators.AccountDetailsValidators;
import com.vesapay.creditcard.models.AddAccountDto;
import com.vesapay.creditcard.models.UserResponseDto;
import com.vesapay.creditcard.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountsApi {

    private final Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountDetailsValidators accountDetailsValidators;

    @Autowired
    private AccountServiceImpl accountService;

    @Override
    public ResponseEntity<UserResponse>addAccount(AddAccountRequest addAccountRequest){

         logger.info("Onboarding new Customer for the phoneNumber ={}",addAccountRequest.getPhone());
            accountDetailsValidators.AddAccountDetailsValidator(addAccountRequest);
            AddAccountDto addAccountDto = AddAccountMapper.INSTANCE.toDto(addAccountRequest);
            UserResponseDto userResponseDto = accountService.addAccount(addAccountDto);
            UserResponse userResponse = AddAccountMapper.INSTANCE.fromDto(userResponseDto);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse>getUserDetails(String  accountId){
        logger.info("Get User Details Api called for accountId={}",accountId);
        accountDetailsValidators.AccountIdValidator(accountId);
        UserResponseDto userResponseDto = accountService.getByAccountId(accountId);
        UserResponse userResponse = AddAccountMapper.INSTANCE.fromDto(userResponseDto);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }




}
