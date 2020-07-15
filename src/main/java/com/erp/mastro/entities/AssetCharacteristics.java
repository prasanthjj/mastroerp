package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="asset_charateristics")

public class AssetCharacteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "asset_characters")
    private String character;

    @Column(name = "asset_values")
    private String value;

    @Column(name = "asset_remarks")
    private String assetRemarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "asset_assetcharacteristics", joinColumns = {@JoinColumn(name = "characteristics_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")})
    private Assets asset;

}
