package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class POInvoiceRequestModel {

    private Long id;
    private Long poId;
    private Long branchId;
    private Double totalAmt;
    private Double roundValue;
    private Double grandTotal;
    private Double totalLoadingCharge;
    private Double totalCess;
    private Double subTotal;
    private Double totalTaxableValue;
    private Double totalCgst;
    private Double totalSgst;
    private String paymentMode;

    @Override
    public String toString() {
        return "POInvoiceRequestModel{" +
                "id=" + id +
                ", poId=" + poId +
                ", branchId=" + branchId +
                ", totalAmt=" + totalAmt +
                ", roundValue=" + roundValue +
                ", grandTotal=" + grandTotal +
                ", totalLoadingCharge=" + totalLoadingCharge +
                ", totalCess=" + totalCess +
                ", subTotal=" + subTotal +
                ", totalTaxableValue=" + totalTaxableValue +
                ", totalCgst=" + totalCgst +
                ", totalSgst=" + totalSgst +
                '}';
    }
}
