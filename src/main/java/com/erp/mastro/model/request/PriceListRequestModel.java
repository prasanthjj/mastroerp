package com.erp.mastro.model.request;

public class PriceListRequestModel {

    private Long id;
    private String pricelistName;
    private String categoryType;
    private String partyType;
    private Double discountPercentage;
    private Double allowedPriceDevPerUpper;
    private Double allowedPriceDevPerLower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPricelistName() {
        return pricelistName;
    }

    public void setPricelistName(String pricelistName) {
        this.pricelistName = pricelistName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getAllowedPriceDevPerUpper() {
        return allowedPriceDevPerUpper;
    }

    public void setAllowedPriceDevPerUpper(Double allowedPriceDevPerUpper) {
        this.allowedPriceDevPerUpper = allowedPriceDevPerUpper;
    }

    public Double getAllowedPriceDevPerLower() {
        return allowedPriceDevPerLower;
    }

    public void setAllowedPriceDevPerLower(Double allowedPriceDevPerLower) {
        this.allowedPriceDevPerLower = allowedPriceDevPerLower;
    }
}
