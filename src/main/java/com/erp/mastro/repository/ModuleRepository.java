package com.erp.mastro.repository;

import com.erp.mastro.entities.Modules;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends CrudRepository<Modules, Long> {

}
