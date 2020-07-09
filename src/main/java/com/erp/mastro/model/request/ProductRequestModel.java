package com.erp.mastro.model.request;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestModel {

    private Long id;
    private String itemCode;
    private String dimension;
    private String colour;
    private String guarantee;
    private String warranty;
    private String propertySize;
    private String baseUOM;
    private String baseQuantity;
    private Long subCategoryId;
    private Long hsnId;
    private List<ProductUOMModel> productUOMModelList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(String propertySize) {
        this.propertySize = propertySize;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public String getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(String baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getHsnId() {
        return hsnId;
    }

    public void setHsnId(Long hsnId) {
        this.hsnId = hsnId;
    }

    public List<ProductUOMModel> getProductUOMModelList() {
        return productUOMModelList;
    }

    public void setProductUOMModelList(List<ProductUOMModel> productUOMModelList) {
        this.productUOMModelList = productUOMModelList;
    }

    public static class ProductUOMModel {

        private Long id;
        private String transactionType;
        private String convertionFactor;
        private Long uomId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getConvertionFactor() {
            return convertionFactor;
        }

        public void setConvertionFactor(String convertionFactor) {
            this.convertionFactor = convertionFactor;
        }

        public Long getUomId() {
            return uomId;
        }

        public void setUomId(Long uomId) {
            this.uomId = uomId;
        }
    }


}
