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
@Table(name="branch")
public class Branch {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="country_name")
    private String countryName;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "branch_prefix")
    private String branchPrefix;

    @Column(name = "local_currency")
    private String localCurrency;

    @Column(name="email_id")
    private String emailId;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="address")
    private String address;

    @Column(name = "website")
    private String website;

    @Column(name = "branch_address")
    private String branchAddress;

    @Column(name = "fax_no")
    private String faxNo;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name="creation_date")
    private Date creationDate;
    
    @Column(name = "delete_status", nullable = false)
    private int branchDeleteStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "branch_branchRegistration", joinColumns = {@JoinColumn(name = "branch_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "branchRegistration_id", referencedColumnName = "id")})
    private BranchRegistration branchRegistration;

    @ManyToMany(mappedBy = "branch")
    private Set<User> user = new HashSet<>();



}
