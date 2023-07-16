package com.vesapay.creditcard.data.repository;

import com.vesapay.creditcard.data.entities.AccountEntity;
import com.vesapay.creditcard.data.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity,Integer> {

    @Query("SELECT e FROM OfferEntity e WHERE e.accountId = :accountId and e.status= :status")
    List<OfferEntity> findByAccountIdAndStatus(@Param("accountId") String accountId,
                                               @Param("status") String status);

}
