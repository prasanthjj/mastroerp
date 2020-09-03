package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pricelist_name")
    private String pricelistName;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "party_type")
    private String partyType;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "allowed_price_dev_per_upper")
    private Double allowedPriceDevPerUpper;

    @Column(name = "allowed_price_dev_per_lower")
    private Double allowedPriceDevPerLower;

    @Column(name = "delete_status", nullable = false)
    private int pricelistDeleteStatus;

    @OneToMany(mappedBy = "priceList",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Party> partySet = new HashSet<>();

    public PriceList(long l, String s, String s1, String s2, double v, double v1, double v2) {

    }

    public PriceList() {

    }
}
