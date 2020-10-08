package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "credit_details")
public class CreditDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credit_limit")
    private String creditLimit;

    @Column(name = "credit_days")
    private String creditDays;

    @Column(name = "credit_worthiness")
    private String creditWorthiness;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "remarks")
    private String remarks;

    @OneToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "party_credit_details", joinColumns = {@JoinColumn(name = "credit_details_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "id")})
    private Party party;

}
