package com.erp.mastro.model.request;

public class PriceListRequestModel {

    private Long id;
    private String name;
    private String categoryType;
    private String partyType;
    private double discountPercentage;
    private double allowedPriceDevPerUpper;
    private double allowedPriceDevPerLower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getAllowedPriceDevPerUpper() {
        return allowedPriceDevPerUpper;
    }

    public void setAllowedPriceDevPerUpper(double allowedPriceDevPerUpper) {
        this.allowedPriceDevPerUpper = allowedPriceDevPerUpper;
    }

    public double getAllowedPriceDevPerLower() {
        return allowedPriceDevPerLower;
    }

    public void setAllowedPriceDevPerLower(double allowedPriceDevPerLower) {
        this.allowedPriceDevPerLower = allowedPriceDevPerLower;
    }
}
