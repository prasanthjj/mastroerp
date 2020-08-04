package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class CategoryRequestModel {

    private Long id;
    private String categoryName;
    private String categoryDescription;
    private String categoryShortCode;
    private String categoryType;
    private Long catalogId;

    @Override
    public String toString() {
        return "CategoryRequestModel{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                ", categoryShortCode='" + categoryShortCode + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", catalogId=" + catalogId +
                '}';
    }
}
