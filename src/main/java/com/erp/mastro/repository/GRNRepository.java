package com.erp.mastro.repository;

import com.erp.mastro.entities.GRN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GRNRepository extends CrudRepository<GRN, Long> {
}
