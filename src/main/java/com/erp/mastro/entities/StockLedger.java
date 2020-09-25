package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "stock_ledger")
public class StockLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id")
    private Uom baseUom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "opening_stock")
    private Double openingStock;

    @Column(name = "closing_stock")
    private Double closingStock;

    @Column(name = "received_stock")
    private Double receivedStock;

    @Column(name = "issued_stock")
    private Double issuedStock;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "creation_date")
    private Date creationDate;
}
