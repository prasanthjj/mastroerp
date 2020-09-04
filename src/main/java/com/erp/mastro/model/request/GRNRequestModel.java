package com.erp.mastro.model.request;

import com.erp.mastro.entities.GRN;
import com.erp.mastro.entities.IndentItemPartyGroup;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.ProductPartyRateRelation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class GRNRequestModel {

    private Long id;
    private String receivedAganist;
    private Long recevedFromParty;
    private Long poId;
    private Long selectedParty;
    private String receivedAs;
    private String partyInvoiceNo;
    private Date partyInvoiceDate;
    private String spartyInvoiceDate;
    private String receivedThrough;
    private String remarks;
    private Long userInspId;
    private List<GRNRequestModel.GRNPOItemsModel> grnpoItemsModels = new ArrayList<>();

    public GRNRequestModel() {
    }

    public GRNRequestModel(GRN grn) {
        if (grn != null) {
            this.id = grn.getId();
            grn.getPurchaseOrder().getIndentItemPartyGroups().parallelStream().forEach(x -> this.grnpoItemsModels.add(new GRNRequestModel.GRNPOItemsModel(x)));

        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class GRNPOItemsModel {

        private Long id;
        private Product itemId;
        private Long itemPartyGroupId;
        private Double pending;
        private Double received;
        private Double quantityDc;
        private Double shortage;
        private Double accepted;
        private Double rejected;
        private Double quantity;
        private Double rate;
        private Double discount;

        public GRNPOItemsModel() {
        }

        public GRNPOItemsModel(IndentItemPartyGroup indentItemPartyGroup) {
            if (indentItemPartyGroup != null) {
                this.itemPartyGroupId = indentItemPartyGroup.getId();
                this.itemId = indentItemPartyGroup.getItemStockDetails().getStock().getProduct();
                this.quantity = indentItemPartyGroup.getQuantity();
                this.rate = indentItemPartyGroup.getRate();
                ProductPartyRateRelation productPartyRateRelation = indentItemPartyGroup.getParty().getProductPartyRateRelations().stream()
                        .filter(productPartyRateRelation1 -> (null != productPartyRateRelation1))
                        .filter(productPartyRateRelation1 -> (productPartyRateRelation1.getProduct().getId().equals(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getId())))
                        .findFirst().get();
                if (productPartyRateRelation != null) {
                    this.discount = productPartyRateRelation.getPartyPriceList().getDiscount();
                }

            }

        }

    }

    @Override
    public String toString() {
        return "GRNRequestModel{" +
                "id=" + id +
                ", receivedAganist='" + receivedAganist + '\'' +
                ", recevedFromParty=" + recevedFromParty +
                ", poId=" + poId +
                ", selectedParty=" + selectedParty +
                ", receivedAs='" + receivedAs + '\'' +
                ", partyInvoiceNo='" + partyInvoiceNo + '\'' +
                ", partyInvoiceDate=" + partyInvoiceDate +
                ", spartyInvoiceDate='" + spartyInvoiceDate + '\'' +
                ", receivedThrough='" + receivedThrough + '\'' +
                ", remarks='" + remarks + '\'' +
                ", userInspId=" + userInspId +
                ", grnpoItemsModels=" + grnpoItemsModels +
                '}';
    }
}