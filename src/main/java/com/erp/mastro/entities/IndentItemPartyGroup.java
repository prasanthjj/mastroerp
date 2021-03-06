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
@Table(name = "indent_item_party_group")
public class IndentItemPartyGroup extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "indent_item_id")
    private ItemStockDetails itemStockDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_id")
    private Indent indent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "po_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "indentItemPartyGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<GRNItems> grnItems = new HashSet<>();

    @Column(name = "grn_pending_status", nullable = false)
    private int grnPendingStatus;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "cgst_rate")
    private Double cgstRate;

    @Column(name = "sgst_rate")
    private Double sgstRate;

    @Column(name = "cess_rate")
    private Double cessRate;

}
