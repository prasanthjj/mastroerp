package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "contact_details")
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_person_name")
    private String contactPersonName;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "alt_telephone_no")
    private String altTelephoneNo;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "alt_mobile_no")
    private String altMobileNo;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "email_id")
    private String emailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "party_contact_details", joinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")})
    private Party party;

}
