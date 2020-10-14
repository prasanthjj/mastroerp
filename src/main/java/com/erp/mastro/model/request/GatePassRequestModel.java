package com.erp.mastro.model.request;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.entities.GatePass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class GatePassRequestModel {

    private Long id;
    private String vehicleMovementType;
    private String emptyMaterial;
    private String entryNo;
    private Date entryDate;
    private String sEntryDate;
    private String vehicleNo;
    private String transportName;
    private String transportAddress;
    private String preparedBy;
    private String materialDescription;
    private String remarks;
    private String referenceDocumentNo;
    private String customerVendorName;
    private String customerVendorAddress;
    private String LRNo;

    public GatePassRequestModel() {
    }

    public GatePassRequestModel(GatePass gatePass) {
        if (gatePass != null) {
            this.id = gatePass.getId();
            this.vehicleMovementType = gatePass.getVehicleMovementType();
            if (gatePass.getVehicleMovementType().equals("Inward")) {
                gatePass.setVehicleMovementType("Inward");
            } else {
                gatePass.setVehicleMovementType("Outward");
            }
            this.emptyMaterial = gatePass.getEmptyMaterial();
            if (gatePass.getEmptyMaterial().equals("Empty")) {
                gatePass.setEmptyMaterial("Empty");
            } else {
                gatePass.setEmptyMaterial("With_Matrial");
            }
            this.setEntryNo(gatePass.getEntryNo());
            this.setEntryDate(gatePass.getEntryDate());
            this.setVehicleNo(gatePass.getVehicleNo());
            this.setTransportName(gatePass.getTransportName());
            this.setTransportAddress(gatePass.getTransportAddress());
            this.setPreparedBy(gatePass.getPreparedBy());
            this.setMaterialDescription(gatePass.getMaterialDescription());
            this.setRemarks(gatePass.getRemarks());
            this.setReferenceDocumentNo(gatePass.getReferenceDocumentNo());
            this.setCustomerVendorName(gatePass.getCustomerVendorName());
            this.setCustomerVendorAddress(gatePass.getCustomerVendorAddress());
            this.setLRNo(gatePass.getLRNo());

        }
    }

    public String getsEntryDate() {
        return MastroApplicationUtils.getStringFromDate(sEntryDate, getEntryDate());
    }

    public void setsEntryDate(String sEntryDate) {
        this.sEntryDate = sEntryDate;
    }

    @Override
    public String toString() {
        return "GatePassRequestModel{" +
                "id=" + id +
                ", vehicleMovementType='" + vehicleMovementType + '\'' +
                ", emptyMaterial='" + emptyMaterial + '\'' +
                ", entryNo='" + entryNo + '\'' +
                ", entryDate=" + entryDate +
                ", sEntryDate='" + sEntryDate + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", transportName='" + transportName + '\'' +
                ", transportAddress='" + transportAddress + '\'' +
                ", preparedBy='" + preparedBy + '\'' +
                ", materialDescription='" + materialDescription + '\'' +
                ", remarks='" + remarks + '\'' +
                ", referenceDocumentNo='" + referenceDocumentNo + '\'' +
                ", customerVendorName='" + customerVendorName + '\'' +
                ", customerVendorAddress='" + customerVendorAddress + '\'' +
                ", LRNo='" + LRNo + '\'' +
                '}';
    }
}
