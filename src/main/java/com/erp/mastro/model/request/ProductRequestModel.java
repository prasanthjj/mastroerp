package com.erp.mastro.model.request;

import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.ProductUOM;
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
    private String productname;
    private String toleranceType;
    private Double amount;
    private Double percentageAmount;

    public ProductRequestModel() {

    }

    public ProductRequestModel(Product product) {
        if (product != null) {
            this.id = product.getId();
            this.setBasePrice(product.getBasePrice());
            this.setBaseQuantity(product.getBaseQuantity());
            this.setBaseUOM(product.getUom().getId());
            this.setBrandId(product.getBrand().getId());
            this.setHsnId(product.getHsn().getId());
            this.setSubCategoryId(product.getSubCategory().getId());
            this.setColour(product.getColour());
            this.setInspectionType(product.getInspectionType());
            this.setDimension(product.getDimension());
            this.setGuarantee(product.getGuarantee());
            this.setWarranty(product.getWarranty());
            this.setPropertySize(product.getPropertySize());
            this.setLoadingCharge(product.getLoadingCharge());
            this.setToleranceType(product.getToleranceType());
            if (product.getToleranceType().equals("Flat-Amount")) {
                this.amount = product.getAmount();
            } else {
                this.percentageAmount = product.getAmount();
            }
            product.getProductUOMSet().parallelStream().forEach(x -> this.productUOMModelList.add(new ProductRequestModel.ProductUOMModel(x)));
        }
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class ProductUOMModel {

        private Long id;
        private String transactionType;
        private Double convertionFactor;
        private Long uomId;
        private String nameuom;

        public ProductUOMModel() {

        }

        public ProductUOMModel(ProductUOM productUOM) {
            this.id = productUOM.getId();
            this.transactionType = productUOM.getTransactionType();
            this.convertionFactor = productUOM.getConvertionFactor();
            this.uomId = productUOM.getUom().getId();
        }

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
