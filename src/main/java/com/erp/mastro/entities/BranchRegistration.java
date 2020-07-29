package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name="branch_registration")
public class BranchRegistration {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "tan_no")
    private String tanNo;

    @Column(name = "vat_tin_no")
    private String vatTinNo;

    @Column(name = "vat_date")
    private Date vatDate;

    @Column(name = "cst_tin_no")
    private String cstTinNo;

    @Column(name = "cst_date")
    private Date cstdate ;

    @Column(name = "juridical")
    private String juridical ;

    @Column(name = "stax_No")
    private String sTaxNo;

    @Column(name = "stax_date")
    private Date staxDate ;

    @Column(name = "pan_no")
    private String  panNo;

    @Column(name = "pan_date")
    private String panDate ;

    @Column(name = "pf_account")
    private String  pfAccount;

    @Column(name = "pf_date")
    private String pfDate ;

    @Column(name = "esic_account")
    private String  esicAccount ;

    @Column(name = "esic_date")
    private String esicDate ;

    @Column(name = "ecc_no")
    private String eccNo ;

    @Column(name = "pla_no")
    private String plaNo ;

    @Column(name = "ranges")
    private String range ;

    @Column(name = "divisions")
    private String division ;

    @Column(name = "range_address")
    private String  rangeAddress ;

    @Column(name = "division_address")
    private String  divisionAddress;

    @Column(name = "commissionerate")
    private String Commissionerate ;

    @Column(name = "exempted")
    private String exempted ;

    @Column(name = "Commissionerate_address")
    private String  commissionerateAddress;

    @Column(name = "limits")
    private String  limit;

    @Column(name = "gstin")
    private String  gstIn;

    @Column(name = "cin_no")
    private String  cinNo;

    @Column(name = "cin_date")
    private Date  cinDate;
}
