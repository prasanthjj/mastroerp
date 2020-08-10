package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "party_industry_type")
public class IndustryType extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "industry_type")
    private String industryType;

    @OneToMany(mappedBy = "industryType",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Party> partySet = new HashSet<>();

    @Column(name = "delete_status", nullable = false)
    private int industryTypeDeleteStatus;


}
