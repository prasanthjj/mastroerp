package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @Column(name = "category_short_code")
    private String categoryShortCode;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "delete_status", nullable = false)
    private int categoryDeleteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "catalog_category", joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")})
    private Catalog catalog;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "category_subcategory", joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")})
    private Set<SubCategory> subCategories;

    public Category() {

    }

    public Category(Long id, String categoryName, String categoryDescription, String categoryShortCode, String categoryType, Catalog catalog) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryShortCode = categoryShortCode;
        this.categoryType = categoryType;
        this.catalog = catalog;

    }

}
