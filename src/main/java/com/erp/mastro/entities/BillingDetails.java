package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "billing_details")
public class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "designation")
    private String designation;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "gst")
    private String gst;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "ecc_no")
    private String eccNo;

    @Column(name = "commision_rate")
    private String commisionRate;

    @Column(name = "range_no")
    private String rangeNo;

    @Column(name = "division")
    private String division;

    @Column(name = "range_address")
    private String rangeAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "party_billing_details", joinColumns = {@JoinColumn(name = "billing_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")})
    private Party party;

    public BillingDetails(Long id,String type,String country,String state,String street,String city,String area,String pinCode,String designation,String faxNo,String telephoneNo,String contactPersonName,String gst,String emailId,String eccNo,String commisionRate,String rangeNo,String division,Party party) {

        this.id = id;
        this.type=type;
        this.country=country;
        this.state=state;
        this.street=street;
        this.city=city;
        this.area=area;
        this.pinCode=pinCode;
        this.designation=designation;
        this.faxNo=faxNo;
        this.telephoneNo=telephoneNo;
        this.contactPersonName=contactPersonName;
        this.gst=gst;
        this.emailId=emailId;
        this.eccNo=eccNo;
        this.commisionRate=commisionRate;
        this.rangeNo=rangeNo;
        this.division=division;
        this.party=party;

    }

}
