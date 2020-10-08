package com.erp.mastro.repository;

import com.erp.mastro.entities.PriceList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends CrudRepository<PriceList, Long> {
}
