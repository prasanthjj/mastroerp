package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "bank_details")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "swift_no")
    private String swiftNo;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "bank_address")
    private String bankAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "party_billing_details", joinColumns = {@JoinColumn(name = "bank_details_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")})
    private Party party;

    public BankDetails(Long id,String accountType,String ifscCode,String accountNo,String bankName,String swiftNo,String branchName,String bankAddress,Party party) {

        this.id = id;
        this.accountType=accountType;
        this.ifscCode=ifscCode;
        this.accountNo=accountNo;
        this.bankName=bankName;
        this.swiftNo=swiftNo;
        this.branchName=branchName;
        this.bankAddress=bankAddress;
        this.party=party;

    }

}
