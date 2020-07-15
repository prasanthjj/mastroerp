package com.erp.mastro.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "assets")

public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "asset_no")
    private String assetNo;

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "asset_location")
    private String assetLocation;

    @Column(name="sub_location")
    private String subLocation;

    @Column(name="party_no")
    private String partyNo;

    @Column(name = "hours_utilized")
    private Double hoursUtilized;

    @Column(name="installation_date")
    private Date installationDate;

    @Column(name="effective_date")
    private Date effectiveDate;

    @Column(name="capacity")
    private String capacity;

    @Column(name="maintenance_priority")
    private String maintenancePriority;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "maintenanace_required", nullable = false)
    private Boolean maintenanceRequired;

    @Column(name = "make")
    private String make;

    @Column(name = "delete_status", nullable = false)
    private int assetDeleteStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "asset_assetcharacteristics", joinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "characteristics_id", referencedColumnName = "id")})
    private Set<AssetCharacteristics> assetCharacteristics;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "asset_assetmaintenanceactivities", joinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "maintenanceactivities_id", referencedColumnName = "id")})
    private Set<AssetMaintenanceActivities> assetMaintenanceActivities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "asset_assetchecklist", joinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "checklist_id", referencedColumnName = "id")})
    private Set<AssetChecklist> assetChecklists;

}
