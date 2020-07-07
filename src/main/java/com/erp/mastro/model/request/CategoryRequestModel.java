package com.erp.mastro.model.request;

public class CategoryRequestModel {

    private Long id;
    private String categoryName;
    private String categoryDescription;
    private String categoryShortCode;
    private String categoryType;
    private Long catalogId;

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

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
}
