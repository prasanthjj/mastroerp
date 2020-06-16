package com.erp.mastro.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "subcategory_product", joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")})
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductImages> productImages= new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Set<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImages> productImages) {
        this.productImages = productImages;
    }
}
