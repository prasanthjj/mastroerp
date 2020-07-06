package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "party_type")
    private String partyType;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name = "allowed_price_dev_per_upper")
    private double allowedPriceDevPerUpper;

    @Column(name = "allowed_price_dev_per_lower")
    private double allowedPriceDevPerLower;

}
