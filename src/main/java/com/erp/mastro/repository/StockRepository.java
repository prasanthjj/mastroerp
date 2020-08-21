package com.erp.mastro.repository;

import com.erp.mastro.entities.StockDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<StockDetails, Long> {
}
