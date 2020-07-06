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
@Table(name = "party")
public class Party extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_type")
    private String partyType;

    @Column(name = "party_code")
    private String partyCode;

    @Column(name = "party_name")
    private String partyName;

    @Column(name = "alias_name")
    private String aliasName;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "website")
    private String website;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "refer_by")
    private String referBy;

    @Column(name = "source")
    private String source;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "tranporter_name")
    private String transporterName;

    @Column(name = "party_date")
    private Date partyDate;

    @Column(name = "old_ref_code")
    private String oldReferCode;

    @Column(name = "relationship_mananger")
    private String relationshipMananger;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "transaction_type")
    private String transactionType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_contact_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "id")})
    private Set<ContactDetails> contactDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_billing_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "billing_id", referencedColumnName = "id")})
    private Set<BillingDetails> billingDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_bank_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "bank_details_id", referencedColumnName = "id")})
    private Set<BankDetails> bankDetails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_credit_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "credit_details_id", referencedColumnName = "id")})
    private Set<CreditDetails> creditDetails;

    @ManyToMany(mappedBy = "parties")
    private Set<Product> products = new HashSet<>();

    @OneToOne(mappedBy = "party",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private PartyPriceList partyPriceList;

    public Party(Long id,String partyType,String partyCode,String partyName,String aliasName,String status,String categoryType,String transporterName,Date partyDate,String oldReferCode,String relationshipMananger,String industryType,String emailId,String transactionType) {

        this.id = id;
        this.partyType=partyType;
        this.partyCode=partyCode;
        this.partyName=partyName;
        this.aliasName=aliasName;
        this.status=status;
        this.categoryType=categoryType;
        this.transporterName=transporterName;
        this.partyDate=partyDate;
        this.oldReferCode=oldReferCode;
        this.relationshipMananger=relationshipMananger;
        this.industryType=industryType;
        this.emailId=emailId;
        this.transactionType=transactionType;

    }
}
