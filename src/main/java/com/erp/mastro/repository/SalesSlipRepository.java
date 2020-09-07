package com.erp.mastro.repository;

import com.erp.mastro.entities.SalesSlip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesSlipRepository extends CrudRepository<SalesSlip, Long> {
}
