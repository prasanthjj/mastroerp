package com.erp.mastro.repository;

import com.erp.mastro.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository  extends CrudRepository<Department, Long> {
}
