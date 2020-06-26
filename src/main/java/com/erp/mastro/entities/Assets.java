package com.erp.mastro.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="assets")

public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="asset_name")
    private String assetName;

    @Column(name="asset_type")
    private String assetType;

    @Column(name="asset_location")
    private  String assetLocation;

    @Column(name="sub_location")
    private String subLocation;

    @Column(name="party_no")
    private String partyNo;

    @Column(name="hours_utilized")
    private String hoursUtilized;

    @Column(name="installation_date")
    private Date installationDate;

    @Column(name="effective_date")
    private Date effectiveDate;

    @Column(name="capacity")
    private String capacity;

    @Column(name="maintenance_priority")
    private String maintenancePriority;

    @Column(name = "is_active", nullable = false)
    protected boolean isActive;

    @Column(name = "maintenanace_required", nullable = false)
    protected boolean maintenanceRequired;

    @Column(name="make")
    private String make;

    @OneToOne(mappedBy = "assets",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private AssetCharacteristics assetCharacteristics;

    @OneToOne(mappedBy = "assets",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private AssetMaintenanceActivities assetMaintenanceActivities;

    @OneToOne(mappedBy = "assets",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private AssetChecklist assetChecklist;

    public Assets(long id, String assetName, String assetType, String assetLocation, String subLocation, String partyNo, String hoursUtilized, Date installationDate, Date effectiveDate, String capacity, String maintenancePriority, boolean isActive, boolean maintenanceRequired, String make) {
        this.id = id;
        this.assetName = assetName;
        this.assetType = assetType;
        this.assetLocation = assetLocation;
        this.subLocation = subLocation;
        this.partyNo = partyNo;
        this.hoursUtilized = hoursUtilized;
        this.installationDate = installationDate;
        this.effectiveDate = effectiveDate;
        this.capacity = capacity;
        this.maintenancePriority = maintenancePriority;
        this.isActive = isActive;
        this.maintenanceRequired = maintenanceRequired;
        this.make = make;
    }
}
