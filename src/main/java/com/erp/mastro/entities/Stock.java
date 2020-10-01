package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "stock")
public class Stock extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "opening_stock")
    private Double openingStock;

    @Column(name = "current_stock")
    private Double currentStock;

    @Column(name = "reorder_level")
    private Double reorderLevel;

    @Column(name = "rejected_stock")
    private Double rejectedStock;

    @Column(name = "reserve_stock")
    private Double reserveStock;

    @Column(name = "minimum_order_quantity")
    private Double minimumOrderQuantity;

    @Column(name = "minimum_lead_time")
    private Double minimumLeadTime;

    @Column(name = "maximum_lead_time")
    private Double maximumLeadTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "minimum_stock_quantity")
    private Double minimumStockQuantity;

    @Column(name = "maximum_stock_qunatity")
    private Double maximumStockQuantity;

    @Column(name = "delete_status", nullable = false)
    private int stockDeleteStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "average_consumption")
    private Double averageConsumption;

}
