package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "sales_slip_items")
public class SalesSlipItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_uom_id")
    private ProductUOM productUOM;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_item_id")
    private GRNItems grnItems;

    @Column(name = "grn_quantity_taken")
    private Double grnQty;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesslip_salesslipitems", joinColumns = {@JoinColumn(name = "salesslip_items_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_slip_id", referencedColumnName = "id")})
    private Set<SalesSlip> salesSlips;

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

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "final_amount")
    private Double finalAmount;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "net_price")
    private Double netPrice;

}
