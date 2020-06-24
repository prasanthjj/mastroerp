package com.erp.mastro.entities;

import javax.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "brand_name")
    protected  String brandName;

    @Column(name = "brand_description")
    protected String brandDescription;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public Brand(Long id, String brandName, String brandDescription) {
        this.id = id;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
    }
}
