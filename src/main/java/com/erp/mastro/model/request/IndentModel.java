package com.erp.mastro.model.request;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.entities.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class IndentModel {

    private Long id;
    private String indentPriority;
    private Date indentDate;
    private Long branchId;
    private Long productId;
    private Double quantityToIndent;
    private String soReferenceNo;
    private Long uomId;
    private Uom purchaseUOM;



    private List<IndentModel.IndentItemStockDetailsModel> indentItemStockDetailsModels = new ArrayList<>();

    public IndentModel() {

    }

    public IndentModel(Indent indent) {
        if (indent != null) {
            this.id = indent.getId();
            this.indentPriority = indent.getIndentPriority();
            this.indentDate= indent.getIndentDate();

            indent.getItemStockDetailsSet().parallelStream().forEach(x -> this.indentItemStockDetailsModels.add(new IndentModel.IndentItemStockDetailsModel(x)));

        }
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class IndentItemStockDetailsModel {

        private Long id;
        private Double quantityToIndent;
        private Date requiredByDate;
        private String srequiredByDate;
        private String soReferenceNo;
        private Long stockId;
        private Long uomId;
        private Stock stock;
        private Uom uom;
        private Set<ProductUOM> productUOMS;

        public IndentItemStockDetailsModel() {
        }

        public Date getRequiredByDate() {
            return requiredByDate;
        }

        public void setRequiredByDate(Date requiredByDate) {
            this.requiredByDate = requiredByDate;
        }

        public String getSrequiredByDate() {
            return MastroApplicationUtils.getStringFromDate(srequiredByDate, getRequiredByDate());
        }

        public void setSrequiredByDate(String srequiredByDate) {
            this.srequiredByDate = srequiredByDate;
        }

        public IndentItemStockDetailsModel(ItemStockDetails itemStockDetails) {
            if (itemStockDetails != null) {
                this.id = itemStockDetails.getId();
                this.stock = itemStockDetails.getStock();
                this.quantityToIndent = itemStockDetails.getQuantityToIndent();
                if (itemStockDetails.getRequiredByDate() != null) {
                    this.requiredByDate = itemStockDetails.getRequiredByDate();
                }
                this.uom=itemStockDetails.getPurchaseUOM();
                this.soReferenceNo = itemStockDetails.getSoReferenceNo();
                this.productUOMS = itemStockDetails.getStock().getProduct().getProductUOMSet().stream()
                        .filter(productuomData -> (null != productuomData))
                        .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                        .collect(Collectors.toSet());
            }

        }
    }

    @Override
    public String toString() {
        return "IndentModel{" +
                "id=" + id +
                ", indentPriority='" + indentPriority + '\'' +
                ", indentDate='" + indentDate + '\'' +
                ", branchId=" + branchId +
                ", indentItemStockDetailsModels=" + indentItemStockDetailsModels +
                '}';
    }
}
