package com.vesapay.creditcard.service.impl;

import com.vesapay.creditcard.api.models.UpdateOfferRequest;
import com.vesapay.creditcard.common.enums.LimitType;
import com.vesapay.creditcard.common.enums.StatusType;
import com.vesapay.creditcard.common.exceptions.DataConflictException;
import com.vesapay.creditcard.common.utils.DateUtils;
import com.vesapay.creditcard.common.utils.FieldValidationStatus;
import com.vesapay.creditcard.data.entities.AccountEntity;
import com.vesapay.creditcard.data.entities.OfferEntity;
import com.vesapay.creditcard.data.entities.mappers.OfferEntityMapper;
import com.vesapay.creditcard.data.repository.AccountRepository;
import com.vesapay.creditcard.data.repository.OfferRepository;
import com.vesapay.creditcard.models.OfferLimitDto;
import com.vesapay.creditcard.models.OfferLimitResponseDto;
import com.vesapay.creditcard.service.LimitService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LimitServiceImpl implements LimitService {

    private final Logger logger = LogManager.getLogger(LimitServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OfferRepository offerRepository;
    @Override
    public OfferLimitResponseDto addOffer(OfferLimitDto offerLimitDto) {

        OfferLimitResponseDto offerLimitResponseDto = null;
        Optional<AccountEntity> accountEntityOptional = accountRepository.findByAccountId(offerLimitDto.getAccountId());
        if(accountEntityOptional.isPresent()){
            AccountEntity accountEntity = accountEntityOptional.get();
            logger.info("Account is found with accountId={}",offerLimitDto.getAccountId());
            if(offerLimitDto.getLimitType().equals(LimitType.AMOUNT_LIMIT.toString())){
                if((accountEntity.getAmountLimit()> Integer.parseInt(offerLimitDto.getNewLimit()))){
                    throw new DataConflictException(FieldValidationStatus.AMOUNT_LIMIT_ERROR_CODE,
                            FieldValidationStatus.AMOUNT_LIMIT_ERROR_MSG);
                }

            }
            if(offerLimitDto.getLimitType().equals(LimitType.PER_TRANSACTION_LIMIT.toString())){
                if((accountEntity.getPerTransactionLimit()> Integer.parseInt(offerLimitDto.getNewLimit())) ||
                        Integer.parseInt(offerLimitDto.getNewLimit())>accountEntity.getAmountLimit()){
                    throw new DataConflictException(FieldValidationStatus.AMOUNT_LIMIT_ERROR_CODE,
                            FieldValidationStatus.AMOUNT_LIMIT_ERROR_MSG);
                }

            }
            if(offerLimitDto.getOfferExpiryTime().before(offerLimitDto.getOfferActivationTime())){
                throw new DataConflictException(FieldValidationStatus.EXPIRY_TIME_DATA_INVALID_CODE,
                        FieldValidationStatus.EXPIRY_TIME_DATA_INVALID_MSG);
            }

            String status = StatusType.PENDING.toString();
            OfferEntity offerEntity = OfferEntityMapper.INSTANCE.toEntity(offerLimitDto,status);
            OfferEntity updatedOffer = offerRepository.save(offerEntity);
            logger.info("New Offer Limit is Added to accountId={} with limit={}",
                    offerEntity.getAccountId(),offerEntity.getLimitType());
            offerLimitResponseDto = OfferEntityMapper.INSTANCE.toDto(updatedOffer);

        }
        else{
            logger.info("AccountID is not Exists in System - accountId={}",offerLimitDto.getAccountId());
            throw new DataConflictException(FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_CODE,FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_MSG);
        }

        return offerLimitResponseDto;
    }

    @Override
    public List<OfferLimitResponseDto> getOfferDetails(String accountId, String activeDate) {

        List<OfferLimitResponseDto> offerLimitResponseDtos = null;
        Optional<AccountEntity> accountEntityOptional = accountRepository.findByAccountId(accountId);
        if(accountEntityOptional.isPresent()) {
            logger.info("Account is found with accountId={}",accountId);
            if (StringUtils.hasText(activeDate)) {
                if (DateUtils.isValid(activeDate)) {
                    List<OfferEntity> offerEntities = offerRepository.findByAccountIdAndStatus(accountId, StatusType.PENDING.toString());
                    offerLimitResponseDtos = OfferEntityMapper.INSTANCE.toDtos(getActiveDateList(offerEntities,
                            DateUtils.convertToTimestamp(DateUtils.toDateTime(activeDate))));
                } else {
                    throw new DataConflictException(FieldValidationStatus.ACTIVE_DATE_INVALID_CODE,
                            FieldValidationStatus.ACTIVE_DATE_INVALID_MSG);
                }
            } else {
                List<OfferEntity> offerEntities = offerRepository.findByAccountIdAndStatus(accountId, StatusType.PENDING.toString());
                offerLimitResponseDtos = OfferEntityMapper.INSTANCE.toDtos(offerEntities);
            }
        }
        else {
            logger.info("AccountID is not Exists in System - accountId={}",accountId);
            throw new DataConflictException(FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_CODE,FieldValidationStatus.ACCOUNT_ID_ALREADY_NOT_EXISTS_MSG);
        }
        return offerLimitResponseDtos;
    }

    @Override
    public OfferLimitResponseDto updateOffer(String offerId, UpdateOfferRequest updateOfferRequest) {

        OfferLimitResponseDto offerLimitResponseDto = null;
        Optional<OfferEntity> optionalOfferEntity = offerRepository.findById(Integer.valueOf(offerId));
        OfferEntity offerEntity = optionalOfferEntity.get();
        if(optionalOfferEntity.isPresent()){
            logger.info("Offer is found with OfferId={}",offerId);

            //ACCEPTED Status
            if(updateOfferRequest.getStatus().equals(StatusType.ACCEPTED.toString())) {
                Optional<AccountEntity> accountEntityOptional = accountRepository.findByAccountId(optionalOfferEntity.get().getAccountId());
                AccountEntity accountEntity = accountEntityOptional.get();
                if(offerEntity.getLimitType().equals(LimitType.AMOUNT_LIMIT.toString())){
                    accountEntity.setLastAmountLimit(accountEntity.getAmountLimit());
                    accountEntity.setAmountLimit(Long.valueOf(offerEntity.getNewLimit()));
                    accountEntity.setAccountLimitUpdateTime(new Timestamp(System.currentTimeMillis()));
                    accountRepository.save(accountEntity);
                }
                if(offerEntity.getLimitType().equals(LimitType.PER_TRANSACTION_LIMIT.toString())){
                    //if amountlimit less than per transaction limit
                    if(accountEntity.getAmountLimit()<Integer.parseInt(offerEntity.getNewLimit())){
                        throw new DataConflictException(FieldValidationStatus.AMOUNT_LIMIT_ERROR_CODE,
                                FieldValidationStatus.AMOUNT_LIMIT_ERROR_MSG);
                    }
                    accountEntity.setLastPerTransactionLimit(accountEntity.getPerTransactionLimit());
                    accountEntity.setPerTransactionLimit(Long.valueOf(offerEntity.getNewLimit()));
                    accountEntity.setPerTransactionLimitUpdateTime(new Timestamp(System.currentTimeMillis()));
                    accountRepository.save(accountEntity);
                }
            }
            // if offer is REJECTED for already ACCEPTED offer-> Changing lastLimit to AmountLimit and viceVersa
            if(updateOfferRequest.getStatus().equals(StatusType.REJECTED.toString()) &&
                    offerEntity.getStatus().equals(StatusType.ACCEPTED.toString())) {
                Optional<AccountEntity> accountEntityOptional = accountRepository.findByAccountId(optionalOfferEntity.get().getAccountId());
                AccountEntity accountEntity = accountEntityOptional.get();
                if(offerEntity.getLimitType().equals(LimitType.AMOUNT_LIMIT.toString())){
                    Long lastLimit = accountEntity.getAmountLimit();
                    accountEntity.setAmountLimit(accountEntity.getLastAmountLimit());
                    accountEntity.setLastAmountLimit(lastLimit);
                    accountEntity.setAccountLimitUpdateTime(new Timestamp(System.currentTimeMillis()));
                    accountRepository.save(accountEntity);
                }
                if(offerEntity.getLimitType().equals(LimitType.PER_TRANSACTION_LIMIT.toString())){
                    Long lastLimit = accountEntity.getPerTransactionLimit();
                    accountEntity.setPerTransactionLimit(accountEntity.getLastPerTransactionLimit());
                    accountEntity.setLastPerTransactionLimit(lastLimit);
                    accountEntity.setPerTransactionLimitUpdateTime(new Timestamp(System.currentTimeMillis()));
                    accountRepository.save(accountEntity);
                }
            }
            offerEntity.setStatus(updateOfferRequest.getStatus());
            OfferEntity updatedOfferEntity = offerRepository.save(offerEntity);
            offerLimitResponseDto = OfferEntityMapper.INSTANCE.toDto(updatedOfferEntity);

        }
        else{
            logger.info("OfferId is not Exists in System - offerId={}",offerId);
            throw new DataConflictException(FieldValidationStatus.OFFER_ID_NOT_EXISTS_INVALID_CODE,
                    FieldValidationStatus.OFFER_ID_NOT_EXISTS_INVALID_MSG);
        }
        return offerLimitResponseDto;
    }


    private List<OfferEntity> getActiveDateList(List<OfferEntity>offerEntities,Timestamp activeDateTime){
        List<OfferEntity> offerEntityList = new ArrayList<>();
        for(OfferEntity offerEntity: offerEntities){
            if(offerEntity.getOfferActivationDateTime().before(activeDateTime) &&
                    offerEntity.getOfferExpiryDateTime().after(activeDateTime)){
                offerEntityList.add(offerEntity);
            }
        }
        return offerEntityList;
    }
}
