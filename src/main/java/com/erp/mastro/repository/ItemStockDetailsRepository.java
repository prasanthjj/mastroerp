package com.erp.mastro.repository;

import com.erp.mastro.entities.ItemStockDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStockDetailsRepository extends CrudRepository<ItemStockDetails, Long> {
}
