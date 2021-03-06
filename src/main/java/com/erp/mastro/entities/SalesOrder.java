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

    @Column(name = "special_instructions")
    private String specialInstructions;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "final_total")
    private Double finalTotal;

    @Column(name = "rounod_off")
    private Double roundOff;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "SalesOrder_no")
    private String salesOrderNo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesorder_salesorderproduct", joinColumns = {@JoinColumn(name = "sales_order_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sales_order_product_id", referencedColumnName = "id")})
    private Set<SalesOrderProduct> salesOrderProductSet = new HashSet<>();


    @OneToMany(mappedBy = "salesOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Indent> indents = new HashSet<>();

}
