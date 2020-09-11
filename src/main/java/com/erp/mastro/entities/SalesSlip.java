package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "sales_slip")
public class SalesSlip extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;

    @Column(name = "transport_mode")
    private String transportMode;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "salesslip_salesslipitems", joinColumns = {@JoinColumn(name = "sales_slip_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "salesslip_items_id", referencedColumnName = "id")})
    private Set<SalesSlipItems> salesSlipItemsSet = new HashSet<>();

    @Column(name = "specific_instruction")
    private String specificInst;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status")
    private String status;
}
