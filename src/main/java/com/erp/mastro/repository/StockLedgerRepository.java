package com.erp.mastro.repository;

import com.erp.mastro.entities.StockLedger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockLedgerRepository extends CrudRepository<StockLedger, Long> {
}
