package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "sales_order_product")
public class SalesOrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "taxable_value")
    private Double taxableValue;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "sgst_amount")
    private Double sgstAmount;

    @Column(name = "cgst_amount")
    private Double cgstAmount;

    @Column(name = "single_cgst_amount")
    private Double singlesgstAmount;

    @Column(name = "single_sgst_amount")
    private Double singlecgstAmount;

    @Column(name = "net_price")
    private Double netPrice;

    @Column(name = "final_Taxable_value")
    private Double finalTaxableValue;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesorder_salesorderproduct", joinColumns = {@JoinColumn(name = "sales_order_product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_order_id", referencedColumnName = "id")})
    private Set<SalesOrder> salesOrderSet;

}
