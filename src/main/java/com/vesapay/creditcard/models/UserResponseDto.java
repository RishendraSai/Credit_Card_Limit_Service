package com.vesapay.creditcard.models;

import lombok.Data;

@Data
public class UserResponseDto {

    private String name;
    private String phone;
    private String status;
    private AccountDto account;

}
