package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "indent")
public class Indent extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indent_priority")
    private String indentPriority;


    @Column(name = "indent_Date")
    private Date indentDate;

    @Column(name = "delete_status", nullable = false)
    private int indentDeleteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "indent_status")
    private String indentStatus;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "indent_indentitemstockdetails", joinColumns = {@JoinColumn(name = "indent_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "item_stock_deatils_id", referencedColumnName = "id")})
    private Set<ItemStockDetails> itemStockDetailsSet = new HashSet<>();

    @OneToMany(mappedBy = "indent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();

    @OneToMany(mappedBy = "indent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();


    @OneToMany(mappedBy = "indent",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DeliveryChellan> deliveryChellanSet = new HashSet<>();

}
