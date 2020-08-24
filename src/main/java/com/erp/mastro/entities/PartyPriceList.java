package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "party_pricelist_relation")
public class PartyPriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "allowed_price_dev_per_upper")
    private Double allowedPriceDevPerUpper;

    @Column(name = "allowed_price_dev_per_lower")
    private Double allowedPriceDevPerLower;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "credit_days")
    private Integer creditDays;

    @OneToOne(mappedBy = "partyPriceList",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private ProductPartyRateRelation productPartyRateRelation;

    @Column(name = "remarks")
    private String remarks;

}
