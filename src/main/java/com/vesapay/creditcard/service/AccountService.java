package com.vesapay.creditcard.service;

import com.vesapay.creditcard.api.models.UserResponse;
import com.vesapay.creditcard.models.AddAccountDto;
import com.vesapay.creditcard.models.UserResponseDto;

public interface AccountService {
    public UserResponseDto addAccount(AddAccountDto addAccountDto);

    public UserResponseDto getByAccountId(String accountId);
}
