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
@Table(name = "item_stock_details")
public class ItemStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_to_indent")
    private Double quantityToIndent;

    @Column(name = "required_by_date")
    private Date requiredByDate;

    @Column(name = "so_reference_no")
    private String soReferenceNo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_uom_id")
    private Uom purchaseUOM;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "indent_indentitemstockdetails", joinColumns = {@JoinColumn(name = "item_stock_deatils_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "indent_id", referencedColumnName = "id")})
    private Set<Indent> indents;

    @OneToMany(mappedBy = "itemStockDetails",
            cascade = CascadeType.ALL
    )
    private Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();

    @Column(name = "purchase_quantity")
    private Double purchaseQuantity;

}
