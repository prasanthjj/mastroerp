package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class DepartmentRequestModel {

    private Long id;
    private String departmentName;
    private String departmentHead;
    private boolean status;

}
