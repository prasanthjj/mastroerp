package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity

@Table(name = "salary_component")
public class SalaryComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component_type")
    private String componentType;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "payslip_name")
    private String payslipName;

    @Column(name = "calculation_type")
    private String calculation_Type;

    @Column(name = "amount")
    private String amount;


}
