package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeId(Long id);

    void saveOrUpdateEmployee(Employee employee);

    void deleteEmployee(Long id);

}
