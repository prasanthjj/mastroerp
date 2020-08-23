package com.erp.mastro.repository;

import com.erp.mastro.entities.SalaryComponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryComponentRepository extends CrudRepository<SalaryComponent, Long> {

}
