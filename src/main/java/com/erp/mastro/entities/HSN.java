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

    @Column(name="chapter")
    private String chapter;

    @Column(name="heading")
    private String heading;

    @Column(name="sub_heading")
    private String subHeading;

    @Column(name="hsn_name")
    private String hsn_name;

    @Column(name="gst_goods_name")
    private String gstGoodsName;

    @Column(name="effective_from")
    private Date effectiveFrom;

    @Column(name="sgst")
    private Double sgst;

    @Column(name="cgst")
    private Double cgst;

    @Column(name="igst")
    private Double igst;

    @Column(name="utgst")
    private Double utgst;

    @OneToMany(mappedBy = "hsn",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Product> productSet = new HashSet<>();

    public HSN(Long id, Date entryDate, String section, String chapter, String heading, String subHeading, String hsn_name, String gstGoodsName, Date effectiveFrom, Double sgst, Double cgst, Double igst, Double utgst) {
        this.id = id;
        this.entryDate = entryDate;
        this.section = section;
        this.chapter = chapter;
        this.heading = heading;
        this.subHeading = subHeading;
        this.hsn_name = hsn_name;
        this.gstGoodsName = gstGoodsName;
        this.effectiveFrom = effectiveFrom;
        this.sgst = sgst;
        this.cgst = cgst;
        this.igst = igst;
        this.utgst = utgst;
    }
}


