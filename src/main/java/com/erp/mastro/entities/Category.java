package com.erp.mastro.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "catalog_category", joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")})
    private Catalog catalog;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "category_subcategory", joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")})
    private Set<SubCategory> subCategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryShortCode() {
        return categoryShortCode;
    }

    public void setCategoryShortCode(String categoryShortCode) {
        this.categoryShortCode = categoryShortCode;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
