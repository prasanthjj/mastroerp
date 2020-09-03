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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesorder_salesorderproduct", joinColumns = {@JoinColumn(name = "sales_order_product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_order_id", referencedColumnName = "id")})
    private Set<SalesOrder> salesOrderSet;

}
