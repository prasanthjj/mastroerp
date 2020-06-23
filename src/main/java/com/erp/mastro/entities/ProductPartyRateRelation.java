package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "product_party_rate_relation")
public class ProductPartyRateRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name = "rate")
    private double rate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name = "credit_days")
    private String creditDays;

    @Column(name = "allowed_price_dev_per_upper")
    private double allowedPriceDevPerUpper;

    @Column(name = "allowed_price_dev_per_lower")
    private double allowedPriceDevPerLower;

}
