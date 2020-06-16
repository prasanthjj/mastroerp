package com.erp.mastro.entities;

import javax.persistence.*;

@Entity
@Table(name = "sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @Column(name = "sub_category_description")
    private String subCategoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "category_subcategory", joinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Category category;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryDescription() {
        return subCategoryDescription;
    }

    public void setSubCategoryDescription(String subCategoryDescription) {
        this.subCategoryDescription = subCategoryDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
