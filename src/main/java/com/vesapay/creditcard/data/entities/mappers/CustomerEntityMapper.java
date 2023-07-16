package com.vesapay.creditcard.data.entities.mappers;

import com.vesapay.creditcard.data.entities.CustomersEntity;
import com.vesapay.creditcard.models.AddAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

@Mapper
public interface CustomerEntityMapper {

    CustomerEntityMapper INSTANCE = Mappers.getMapper(CustomerEntityMapper.class);

    CustomersEntity fromDto(AddAccountDto addAccountDto,String status,  Timestamp createdAt, Timestamp updatedAt);



}
