package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class ProductRequestModel {

    private Long id;
    private String dimension;
    private String colour;
    private String guarantee;
    private String warranty;
    private String propertySize;
    private Long baseUOM;
    private Double baseQuantity;
    private Long subCategoryId;
    private Long hsnId;
    private Double basePrice;
    private Double loadingCharge;
    private String inspectionType;
    private Long brandId;
    private List<ProductUOMModel> productUOMModelList = new ArrayList<>();

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class ProductUOMModel {

        private Long id;
        private String transactionType;
        private Double convertionFactor;
        private Long uomId;

    }

    @Override
    public String toString() {
        return "ProductRequestModel{" +
                "id=" + id +
                ", dimension='" + dimension + '\'' +
                ", colour='" + colour + '\'' +
                ", guarantee='" + guarantee + '\'' +
                ", warranty='" + warranty + '\'' +
                ", propertySize='" + propertySize + '\'' +
                ", baseUOM='" + baseUOM + '\'' +
                ", baseQuantity=" + baseQuantity +
                ", subCategoryId=" + subCategoryId +
                ", hsnId=" + hsnId +
                ", basePrice=" + basePrice +
                ", loadingCharge=" + loadingCharge +
                ", inspectionType='" + inspectionType + '\'' +
                ", productUOMModelList=" + productUOMModelList +
                '}';
    }
}
