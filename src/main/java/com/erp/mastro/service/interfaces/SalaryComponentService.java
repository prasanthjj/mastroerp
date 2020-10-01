package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.SalaryComponent;
import com.erp.mastro.model.request.SalaryComponentRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryComponentService {

    List<SalaryComponent> getAllSalaryComponents();

    SalaryComponent getSalaryComponentId(Long id);

    void saveOrUpdateSalaryComponent(SalaryComponentRequestModel salaryComponentRequestModel, String value);

    void activateOrDeactivateSalaryComponent(Long id);

}
