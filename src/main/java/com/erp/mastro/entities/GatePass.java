package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "gate_pass")
public class GatePass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_movement_type")
    private String vehicleMovementType;

    @Column(name = "emptymaterial")
    private String emptyMaterial;

    @Column(name = "entryNo")
    private String entryNo;

    @Column(name = "Entry_date")
    private Date entryDate;

    @Column(name = "vehicle_No")
    private String vehicleNo;

    @Column(name = "transport_name")
    private String transportName;

    @Column(name = "transport_address")
    private String transportAddress;

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "material_description")
    private String materialDescription;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "reference_document_no")
    private String referenceDocumentNo;

    @Column(name = "customer_vendor_name")
    private String customerVendorName;

    @Column(name = "customer_vendor_address")
    private String customerVendorAddress;

    @Column(name = "lr_no")
    private String LRNo;

    @Column(name = "delete_status", nullable = false)
    private int gatepassDeleteStatus;

}
