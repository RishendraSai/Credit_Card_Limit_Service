package com.vesapay.creditcard.data.repository;

import com.vesapay.creditcard.data.entities.AccountEntity;
import com.vesapay.creditcard.data.entities.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.AccessControlContext;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

    @Query("SELECT e FROM AccountEntity e WHERE e.accountId = :accountId")
    Optional<AccountEntity> findByAccountId(@Param("accountId") String accountId);

}
