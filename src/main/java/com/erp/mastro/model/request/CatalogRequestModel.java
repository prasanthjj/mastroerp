package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class CatalogRequestModel {

    private Long id;
    private String catalogName;
    private String catalogDescription;

    @Override
    public String toString() {
        return "CatalogRequestModel{" +
                "id=" + id +
                ", catalogName='" + catalogName + '\'' +
                ", catalogDescription='" + catalogDescription + '\'' +
                '}';
    }
}
