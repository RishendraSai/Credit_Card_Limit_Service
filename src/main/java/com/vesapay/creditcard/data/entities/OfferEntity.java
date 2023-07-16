package com.vesapay.creditcard.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name= "credit_offer_limit")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="account_id")
    private String accountId;

    @Column(name = "offer_activation_datetime")
    private Timestamp offerActivationDateTime;

    @Column(name = "offer_expiry_datetime")
    private Timestamp offerExpiryDateTime;

    @Column(name= "status")
    private String status;

    @Column(name= "limit_type")
    private String limitType;

    @Column(name="new_limit")
    private String newLimit;


}
