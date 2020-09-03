package com.erp.mastro.model.request;

import com.erp.mastro.entities.SalaryComponent;
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
    private Double amount;
    private Double percentageAmount;
    private boolean status;

    public  SalaryComponentRequestModel(){

    }

    public SalaryComponentRequestModel(SalaryComponent salaryComponent) {
        if (salaryComponent!=null) {
            this.id = salaryComponent.getId();
            this.componentType = salaryComponent.getComponentType();
            this.componentName = salaryComponent.getComponentName();
            this.payslipName = salaryComponent.getPayslipName();
            this.calculation_Type = salaryComponent.getCalculation_Type();
            if(salaryComponent.getCalculation_Type().equals("Flat-Amount")){
                this.amount = salaryComponent.getAmount();
            }
           else{
                this.percentageAmount = salaryComponent.getAmount();
            }

            this.status = salaryComponent.isStatus();
        }
    }
}
