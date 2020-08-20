package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class SalaryComponentRequestModel {
    private Long id;
    private String componentType;
    private String componentName;
    private String payslipName;
    private String calculation_Type;
    private String amount;
}
