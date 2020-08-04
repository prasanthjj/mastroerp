package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class SubCategoryRequestModel {

    private Long id;
    private String subCategoryName;
    private String subCategoryDescription;
    private Long categoryId;

    @Override
    public String toString() {
        return "SubCategoryRequestModel{" +
                "id=" + id +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", subCategoryDescription='" + subCategoryDescription + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
