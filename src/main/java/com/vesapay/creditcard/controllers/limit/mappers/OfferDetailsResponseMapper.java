package com.vesapay.creditcard.controllers.limit.mappers;

import com.vesapay.creditcard.api.models.OfferDetailsResponse;
import com.vesapay.creditcard.api.models.OfferResponse;
import com.vesapay.creditcard.common.utils.DateUtils;
import com.vesapay.creditcard.models.OfferLimitResponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsResponseMapper {

    public static OfferDetailsResponse fromDtos(List<OfferLimitResponseDto> offerLimitResponseDtoList){
        OfferDetailsResponse offerDetailsResponse = new OfferDetailsResponse();
        List<OfferResponse> offerDetails = new ArrayList<>();
        for(OfferLimitResponseDto offerLimitResponseDto : offerLimitResponseDtoList){
            OfferResponse offerResponse = new OfferResponse();
            offerResponse.setOfferId(Integer.valueOf(offerLimitResponseDto.getOfferId()));
            offerResponse.setLimitType(offerLimitResponseDto.getLimitType());
            offerResponse.setOfferActivationTime(DateUtils.convertToOffsetDateTime
                    (offerLimitResponseDto.getOfferActivationTime()));
            offerResponse.setOfferExpiryTime(DateUtils.convertToOffsetDateTime(
                    offerLimitResponseDto.getOfferExpiryTime()));
            offerResponse.setAccountId(offerLimitResponseDto.getAccountId());
            offerResponse.setStatus(offerLimitResponseDto.getStatus());
            offerResponse.setNewLimit(offerLimitResponseDto.getNewLimit());
            offerDetails.add(offerResponse);
        }
        offerDetailsResponse.setOfferDetails(offerDetails);
        return offerDetailsResponse;
    }
}
