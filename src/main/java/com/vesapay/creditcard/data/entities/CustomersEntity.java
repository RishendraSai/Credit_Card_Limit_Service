package com.vesapay.creditcard.data.entities;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name ="customers")
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
