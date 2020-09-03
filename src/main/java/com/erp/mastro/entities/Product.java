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

    @Column(name = "inspection_type")
    private String inspectionType;

    @Column(name = "loading_charge")
    private Double loadingCharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @Column(name = "base_quantity")
    private Double baseQuantity;

    @Column(name = "base_price")
    private Double basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "subcategory_product", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")})
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductImages> productImages = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_productuom", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "productuom_id", referencedColumnName = "id")})
    private Set<ProductUOM> productUOMSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hsn_id")
    private HSN hsn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductPartyRateRelation> productPartyRateRelations = new HashSet<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Stock> stockSet = new HashSet<>();

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<SalesOrderProduct> salesOrderProductSet = new HashSet<>();

}
