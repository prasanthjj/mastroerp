package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="asset_maintenance_activities")

public class AssetMaintenanceActivities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="activity_name")
    private String activityName;

    @Column(name="upper_limit")
    private String upperLimit;

    @Column(name="standard_observation")
    private String standardObservation;

    @Column(name = "tolerence_lowerlimit")
    private String tolerenceLowerlimit;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "category")
    private String category;

    @Column(name = "tolerence")
    private Double tolerence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "asset_assetmaintenanceactivities", joinColumns = {@JoinColumn(name = "maintenanceactivities_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")})
    private Assets asset;

}

