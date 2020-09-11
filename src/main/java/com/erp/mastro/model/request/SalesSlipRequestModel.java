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
