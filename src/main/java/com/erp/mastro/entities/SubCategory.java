package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @Column(name = "sub_category_description")
    private String subCategoryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "category_subcategory", joinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "subcategory_product", joinColumns = {@JoinColumn(name = "sub_category_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    private Set<Product> productSet;

}
