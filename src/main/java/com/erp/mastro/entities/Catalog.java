package com.erp.mastro.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDescription() {
        return catalogDescription;
    }

    public void setCatalogDescription(String catalogDescription) {
        this.catalogDescription = catalogDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
