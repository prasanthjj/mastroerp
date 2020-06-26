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

    @Column(name="character")
    private String character;

    @Column(name = "value")
    private String value;

    @Column(name = "remarks")
    private String remarks;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Assets assets;

    public AssetCharacteristics(long id, String character, String value, String remarks) {
        this.id = id;
        this.character = character;
        this.value = value;
        this.remarks = remarks;
    }
}
