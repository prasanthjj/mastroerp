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
@Table(name = "purchase_order")
public class PurchaseOrder extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_id")
    private Indent indent;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "purchaseorder_indent_iteam",
            joinColumns = {@JoinColumn(name = "purchase_order_id")},
            inverseJoinColumns = {@JoinColumn(name = "indent_iteam_id")}
    )
    Set<ItemStockDetails> itemStockDetailsSet = new HashSet<>();

    @OneToMany(mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();

    @Column(name = "reason")
    private String reason;

}
