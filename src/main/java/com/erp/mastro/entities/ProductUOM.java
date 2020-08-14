package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "product_uom")
public class ProductUOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "convertion_factor")
    private Double convertionFactor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "product_productuom", joinColumns = {@JoinColumn(name = "productuom_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    private Product product;

}
