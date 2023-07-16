package com.vesapay.creditcard.data.entities.mappers;


import com.vesapay.creditcard.data.entities.OfferEntity;
import com.vesapay.creditcard.models.OfferLimitDto;
import com.vesapay.creditcard.models.OfferLimitResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OfferEntityMapper {

    OfferEntityMapper INSTANCE = Mappers.getMapper(OfferEntityMapper.class);


    @Mapping(source = "status",target="status")
    @Mapping(source = "offerLimitDto.offerActivationTime",target="offerActivationDateTime")
    @Mapping(source = "offerLimitDto.offerExpiryTime",target="offerExpiryDateTime")
    OfferEntity  toEntity(OfferLimitDto offerLimitDto,String status);

    @Mapping(source = "offerEntity.offerActivationDateTime",target="offerActivationTime")
    @Mapping(source = "offerEntity.offerExpiryDateTime",target="offerExpiryTime")
    @Mapping(source="offerEntity.id",target="offerId")
    OfferLimitResponseDto toDto(OfferEntity offerEntity);


    @Mapping(source = "offerEntity.offerActivationDateTime",target="offerActivationTime")
    @Mapping(source = "offerEntity.offerExpiryDateTime",target="offerExpiryTime")
    @Mapping(source="offerEntity.id",target="offerId")
    List<OfferLimitResponseDto> toDtos(List<OfferEntity> offerEntity);



}

