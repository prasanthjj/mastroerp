package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Department;
import com.erp.mastro.model.request.DepartmentRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    List<Department> getAllDepartment();

    Department getDepartmentById(Long id);

    void saveOrUpdateDepartment(DepartmentRequestModel departmentRequestModel);

    void activateOrDeactivateDepartment(Long id);
}
