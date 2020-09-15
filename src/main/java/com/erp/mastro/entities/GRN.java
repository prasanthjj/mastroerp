package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "grn")
public class GRN extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "received_aganist")
    private String receivedAganist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "party_id")
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id")
    private PurchaseOrder purchaseOrder;

    @Column(name = "received_as")
    private String receivedAs;

    @Column(name = "in_num")
    private String number;

    @Column(name = "in_date")
    private Date date;

    @Column(name = "received_through")
    private String receivedThrough;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insp_user_id")
    private User user;

    @OneToMany(mappedBy = "grn",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private Set<GRNItems> grnItems = new HashSet<>();

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "grn_no")
    private String grnNo;
}
