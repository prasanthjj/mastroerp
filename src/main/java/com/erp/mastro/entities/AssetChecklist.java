package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="asset_checklist")

public class AssetChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "check_list")
    private String checkList;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "asset_assetchecklist", joinColumns = {@JoinColumn(name = "checklist_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "asset_id", referencedColumnName = "id")})
    private Assets asset;

}
