package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catalog_name")
    private String catalogName;

    @Column(name = "catalog_description")
    private String catalogDescription;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "catalog_category", joinColumns = {@JoinColumn(name = "catalog_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Set<Category> categories;

    public Catalog(Long id, String catalogName, String catalogDescription) {

        this.id = id;
        this.catalogName = catalogName;
        this.catalogDescription=catalogDescription;

    }

}
