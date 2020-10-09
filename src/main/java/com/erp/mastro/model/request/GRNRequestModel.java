package com.erp.mastro.model.request;

import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

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
            grn.getPurchaseOrder().getIndentItemPartyGroups()
                    .parallelStream()
                    .filter(grnIndentItemGroupData -> (1 != grnIndentItemGroupData.getGrnPendingStatus()))
                    .forEach(x -> this.grnpoItemsModels.add(new GRNRequestModel.GRNPOItemsModel(x)));

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
        private String nameOfProduct;
        private String uomPurchase;
        private String uomBase;
        private Double itemTotal;
        private Double itemCessAmt;
        private Double itemCgstAmt;
        private Double itemSgstAmt;
        private String hsncode;
        private String grnno;
        private Double finalItemTotal;

        public GRNPOItemsModel() {
        }

        public GRNPOItemsModel(IndentItemPartyGroup indentItemPartyGroup) {
            if (indentItemPartyGroup != null) {
                this.itemPartyGroupId = indentItemPartyGroup.getId();
                this.itemId = indentItemPartyGroup.getItemStockDetails().getStock().getProduct();
                this.quantity = indentItemPartyGroup.getQuantity();
                this.rate = indentItemPartyGroup.getRate();
                Set<GRN> grns = indentItemPartyGroup.getPurchaseOrder().getGrnSet().stream()
                        .filter(grnData -> (null != grnData))
                        .filter(grnData -> (!grnData.getStatus().equals(Constants.STATUS_DISCARD)))
                        .collect(Collectors.toSet());
                if (grns.isEmpty() == false) {
                    Set<GRNItems> grnItemsSet = new HashSet<>();
                    for (GRN grn : grns) {
                        if (grn.getGrnItems().isEmpty() == false) {
                            GRNItems grnItem = grn.getGrnItems().stream()
                                    .filter(grnItemData -> (null != grnItemData))
                                    .filter(grnItemData -> (grnItemData.getIndentItemPartyGroup().getId().equals(indentItemPartyGroup.getId())))
                                    .findFirst().get();

                            grnItemsSet.add(grnItem);
                        }
                    }
                    if (grnItemsSet.isEmpty() == false) {
                        Double totalAccepted = 0.0d;
                        for (GRNItems grnItems : grnItemsSet) {
                            totalAccepted = totalAccepted + grnItems.getAccepted();
                        }
                        this.pending = indentItemPartyGroup.getQuantity() - totalAccepted;
                    } else {
                        this.pending = indentItemPartyGroup.getQuantity();
                    }
                } else {
                    this.pending = indentItemPartyGroup.getQuantity();
                }
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

    //for salesslip grnitem view
    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class GRNItemModel {

        private Long id;
        private Product itemId;
        private Long itemPartyGroupId;
        private Double acceptedqty;
        private String purchaseuom;
        private String itemname;
        private Long grnid;
        private Double acceptedqtyinsalesuom;
        private String salesuom;
        private String grnno;

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