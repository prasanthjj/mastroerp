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
@Table(name = "grn_items")
public class GRNItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_id")
    private GRN grn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_item_group_id")
    private IndentItemPartyGroup indentItemPartyGroup;

    @Column(name = "pending")
    private Double pending;

    @Column(name = "received")
    private Double received;

    @Column(name = "shortage")
    private Double shortage;

    @Column(name = "accepted")
    private Double accepted;

    @Column(name = "rejected")
    private Double rejected;

    @Column(name = "quantity_dc")
    private Double quantityDc;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "cgst_rate")
    private Double cgstRate;

    @Column(name = "cgst_amount")
    private Double cgstAmount;

    @Column(name = "sgst_rate")
    private Double sgstRate;

    @Column(name = "sgst_amount")
    private Double sgstAmount;

    @Column(name = "igst_rate")
    private Double igstRate;

    @Column(name = "igst_amount")
    private Double igstAmount;

    @Column(name = "cess_rate")
    private Double cessRate;

    @Column(name = "cess_amount")
    private Double cessAmount;

    @OneToMany(mappedBy = "grnItems",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<SalesSlipItems> salesSlipItems = new HashSet<>();
}
