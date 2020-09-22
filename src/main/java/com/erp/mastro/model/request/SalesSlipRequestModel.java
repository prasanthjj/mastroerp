package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class SalesSlipRequestModel {

    private Long id;
    private String transportMode;
    private String vehicleNo;
    private Long selectedPartyInSalesSlip;
    private String specificInst;
    private String remarks;
    private Double totalAmt;
    private Double roundOff;
    private Double grandTotal;
    private Double totalLoadingCharge;
    private Double totalCess;
    private Double subTotal;
    private Double totalTaxableValue;
    private Double totalCgst;
    private Double totalSgst;
    private Double loadingChargeSum;
    private Double loadingChargeCgst;
    private Double loadingChargesgst;

    @Override
    public String toString() {
        return "SalesSlipRequestModel{" +
                "id=" + id +
                ", transportMode='" + transportMode + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", partyId=" + selectedPartyInSalesSlip +
                '}';
    }
}
