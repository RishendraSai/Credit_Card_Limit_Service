package com.vesapay.creditcard.controllers.accounts.mappers;

import com.vesapay.creditcard.api.models.AddAccountRequest;
import com.vesapay.creditcard.api.models.UserResponse;
import com.vesapay.creditcard.models.AddAccountDto;
import com.vesapay.creditcard.models.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddAccountMapper {

    AddAccountMapper INSTANCE = Mappers.getMapper(AddAccountMapper.class);

    AddAccountDto toDto(AddAccountRequest addAccountRequest);

    UserResponse fromDto(UserResponseDto userResponseDto);
}
