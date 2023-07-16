package com.vesapay.creditcard.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "Accounts")
public class AccountEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="account_id")
    private String accountId;

    @Column(name = "account_limit")
    private Long amountLimit;

    @Column(name = "last_account_limit")
    private Long lastAmountLimit;

    @Column(name = "per_transaction_limit")
    private Long perTransactionLimit;

    @Column(name = "last_per_transaction_limit")
    private Long lastPerTransactionLimit;

    @Column(name = "account_limit_update_time")
    private Timestamp accountLimitUpdateTime;

    @Column(name = "per_transaction_limit_update_time")
    private Timestamp perTransactionLimitUpdateTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomersEntity customersEntity;


}
