package com.vesapay.creditcard.controllers.limit.mappers;


import com.sun.istack.Interned;
import com.vesapay.creditcard.api.models.AddOfferRequest;
import com.vesapay.creditcard.api.models.OfferDetailsResponse;
import com.vesapay.creditcard.api.models.OfferResponse;
import com.vesapay.creditcard.controllers.accounts.mappers.AddAccountMapper;
import com.vesapay.creditcard.models.OfferLimitDto;
import com.vesapay.creditcard.models.OfferLimitResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import com.vesapay.creditcard.common.utils.DateUtils;

@Mapper
public interface OfferRequestMapper {

    OfferRequestMapper INSTANCE = Mappers.getMapper(OfferRequestMapper.class);

    @Mapping(target = "offerActivationTime", source = "offerActivationTime", qualifiedByName = "ToTimestampTimeMapper")
    @Mapping(target = "offerExpiryTime", source = "offerExpiryTime", qualifiedByName = "ToTimestampTimeMapper")
    OfferLimitDto toDto(AddOfferRequest addOfferRequest);



    @Mapping(target = "offerActivationTime", source = "offerActivationTime", qualifiedByName = "ToOffSetTimeMapper")
    @Mapping(target = "offerExpiryTime", source = "offerExpiryTime", qualifiedByName = "ToOffSetTimeMapper")
    OfferResponse fromDto(OfferLimitResponseDto offerLimitResponseDto);


//    @Mapping(target = "offerActivationTime", source = "offerActivationTime", qualifiedByName = "ToOffSetTimeMapper")
//    @Mapping(target = "offerExpiryTime", source = "offerExpiryTime", qualifiedByName = "ToOffSetTimeMapper")
//    @Mapping(target ="offerDetails", expression = "java((offerLimitResponseDto != null) ? offerLimitResponseDto : Collections.emptyList())")
//    OfferDetailsResponse fromDtos(List<OfferLimitResponseDto> offerLimitResponseDto);


    @Named("ToOffSetTimeMapper")
    static OffsetDateTime map(Timestamp dateTime) {
        return DateUtils.convertToOffsetDateTime(dateTime);
    }

    @Named("ToTimestampTimeMapper")
    static Timestamp map(OffsetDateTime dateTime) {
       return DateUtils.convertToTimestamp(dateTime);
    }
}
