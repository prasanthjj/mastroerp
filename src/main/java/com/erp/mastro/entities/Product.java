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
@Table(name = "product")
public class Product extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "colour")
    private String colour;

    @Column(name = "guarantee")
    private String guarantee;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "property_size")
    private String propertySize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "subcategory_product", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")})
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductImages> productImages= new HashSet<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductUOM> productUOMSet= new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_id")
    private HSN hsn;

    @Column(name = "base_uom")
    private String baseUOM;

    @Column(name = "base_quantity")
    private String baseQuantity;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_party",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "party_id") }
    )
    Set<Party> parties = new HashSet<>();


}
