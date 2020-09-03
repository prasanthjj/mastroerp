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
@Table(name = "sales_order")
public class SalesOrder extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesorder_salesorderproduct", joinColumns = {@JoinColumn(name = "sales_order_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_order_product_id", referencedColumnName = "id")})
    private Set<SalesOrderProduct> salesOrderProductSet = new HashSet<>();

}
