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

    @Column(name = "rate")
    private Double rate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesslip_salesslipitems", joinColumns = {@JoinColumn(name = "salesslip_items_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_slip_id", referencedColumnName = "id")})
    private Set<SalesSlip> salesSlips;
}
