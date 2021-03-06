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
@Table(name="hsn")

public class HSN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="entry_date")
    private Date entryDate;

    @Column(name="section")
    private String section;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "heading")
    private String heading;

    @Column(name = "sub_heading")
    private String subHeading;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "gst_goods_name")
    private String gstGoodsName;

    @Column(name = "effective_from")
    private Date effectiveFrom;

    @Column(name = "sgst")
    private Double sgst;

    @Column(name = "cgst")
    private Double cgst;

    @Column(name = "igst")
    private Double igst;

    @Column(name = "utgst")
    private Double utgst;

    @Column(name = "cess")
    private Double cess;

    @OneToMany(mappedBy = "hsn", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Product> productSet = new HashSet<>();

    @Column(name = "delete_status", nullable = false)
    private int hsnDeleteStatus;

}


