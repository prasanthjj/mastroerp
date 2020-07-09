package com.erp.mastro.repository;

import com.erp.mastro.entities.Uom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOMRepository extends CrudRepository<Uom, Long> {
}
