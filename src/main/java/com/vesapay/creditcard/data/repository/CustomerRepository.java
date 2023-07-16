package com.vesapay.creditcard.data.repository;

import com.vesapay.creditcard.data.entities.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomersEntity, Long> {

    @Query("SELECT e FROM CustomersEntity e WHERE e.phone = :phone")
    Optional<CustomersEntity> findByPhoneNumber(@Param("phone") String phone);

}
