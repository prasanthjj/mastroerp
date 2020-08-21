package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "stock_details")
public class StockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_stock")
    private Long openingStock;

    @Column(name = "current_stock")
    private Long currentStock;

    @Column(name = "reorder_level")
    private int reorderLevel;

    @Column(name = "rejected_stock")
    private int rejectedStock;

    @Column(name = "reserve_stock")
    private int reserveStock;

    @Column(name = "minimum_order_quantity")
    private int minimumOrderQuantity;

    @Column(name = "minimum_lead_time")
    private int minimumLeadTime;

    @Column(name = "maximum_lead_time")
    private Long maximumLeadTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "minimum_stock_quantity")
    private int minimumStockQuantity;

    @Column(name = "maximum_stock_qunatity")
    private Long maximumStockQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_selected_branch_id")
    private UserSelectedBranch userSelectedBranch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

}
