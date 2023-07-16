package com.vesapay.creditcard.service;

import com.vesapay.creditcard.api.models.UpdateOfferRequest;
import com.vesapay.creditcard.models.OfferLimitDto;
import com.vesapay.creditcard.models.OfferLimitResponseDto;

import java.util.List;

public interface LimitService {

    public OfferLimitResponseDto addOffer(OfferLimitDto offerLimitDto);

    public List<OfferLimitResponseDto> getOfferDetails(String accountId, String activeDate);

    public OfferLimitResponseDto updateOffer(String offerId, UpdateOfferRequest updateOfferRequest);


}
