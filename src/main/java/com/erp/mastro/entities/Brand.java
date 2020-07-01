package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "brand_name")
    protected String brandName;

    @Column(name = "brand_description")
    protected String brandDescription;

    @Column(name = "delete_status", nullable = false)
    private int brandDeleteStatus;

}
