package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Table(name = "delivery_chellan")
public class DeliveryChellan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "delivery_branch", joinColumns = {@JoinColumn(name = "delivery_chellan_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "branch_id", referencedColumnName = "id")})
    private Branch branch;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_id")
    private Indent indent;



}
