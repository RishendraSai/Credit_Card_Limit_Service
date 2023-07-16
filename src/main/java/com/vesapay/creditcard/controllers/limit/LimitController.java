package com.vesapay.creditcard.controllers.limit;

import com.vesapay.creditcard.api.OffersApi;
import com.vesapay.creditcard.api.models.AddOfferRequest;
import com.vesapay.creditcard.api.models.OfferDetailsResponse;
import com.vesapay.creditcard.api.models.OfferResponse;
import com.vesapay.creditcard.api.models.UpdateOfferRequest;
import com.vesapay.creditcard.controllers.accounts.AccountController;
import com.vesapay.creditcard.controllers.limit.mappers.OfferDetailsResponseMapper;
import com.vesapay.creditcard.controllers.limit.mappers.OfferRequestMapper;
import com.vesapay.creditcard.controllers.limit.validators.OfferDetailsValidator;
import com.vesapay.creditcard.models.OfferLimitDto;
import com.vesapay.creditcard.models.OfferLimitResponseDto;
import com.vesapay.creditcard.service.impl.LimitServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LimitController implements OffersApi {

    private final Logger logger = LogManager.getLogger(LimitController.class);

    @Autowired
    private OfferDetailsValidator offerDetailsValidator;

    @Autowired
    private LimitServiceImpl limitService;

    @Override
    public ResponseEntity<OfferResponse> addOffer(AddOfferRequest offerRequest){

        logger.info("Adding new Offer for the accountId={}",offerRequest.getAccountId());
        offerDetailsValidator.AddOfferRequestValidator(offerRequest);
        OfferLimitDto offerLimitDto = OfferRequestMapper.INSTANCE.toDto(offerRequest);
        OfferLimitResponseDto offerResponseLimit = limitService.addOffer(offerLimitDto);
        OfferResponse offerResponse = OfferRequestMapper.INSTANCE.fromDto(offerResponseLimit);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<OfferDetailsResponse> getOfferDetails(String accountId, String activeDate){
        logger.info("Get Offer Details Api called for accountId={}",accountId);
        offerDetailsValidator.getOfferDetailsValidator(accountId);
        List<OfferLimitResponseDto> offerLimitResponseDtos = limitService.getOfferDetails(accountId,activeDate);
        OfferDetailsResponse offerDetailsResponse = OfferDetailsResponseMapper.fromDtos(offerLimitResponseDtos);
        return new ResponseEntity<>(offerDetailsResponse,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<OfferResponse>updateOffer(String offerId, UpdateOfferRequest updateOfferRequest){
        offerDetailsValidator.updateOfferDetailsValidator(offerId,updateOfferRequest);
        OfferLimitResponseDto offerResponseLimit = limitService.updateOffer(offerId,updateOfferRequest);
        OfferResponse offerResponse = OfferRequestMapper.INSTANCE.fromDto(offerResponseLimit);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }





}
