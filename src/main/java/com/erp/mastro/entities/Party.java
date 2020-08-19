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

    @Column(name = "status")
    private String status;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "party_date")
    private Date partyDate;

    @Column(name = "old_ref_code")
    private String oldReferCode;

    @Column(name = "relationship_mananger")
    private String relationshipMananger;

    @Column(name = "enabled", nullable = false)
    protected boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_contact_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "id")})
    private Set<ContactDetails> contactDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_billing_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "billing_id", referencedColumnName = "id")})
    private Set<BillingDetails> billingDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_bank_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "bank_details_id", referencedColumnName = "id")})
    private Set<BankDetails> bankDetails = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "party_credit_details", joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "credit_details_id", referencedColumnName = "id")})
    private Set<CreditDetails> creditDetails = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private IndustryType industryType;

    @OneToMany(mappedBy = "party",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProductPartyRateRelation> productPartyRateRelations = new HashSet<>();

}
