package com.erp.mastro.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_uom")
public class ProductUOM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "convertion_factor")
    private String convertionFactor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uom_id")
    private UOM uom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getConvertionFactor() {
        return convertionFactor;
    }

    public void setConvertionFactor(String convertionFactor) {
        this.convertionFactor = convertionFactor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }
}
