package com.erp.mastro.repository;

import com.erp.mastro.entities.GRNItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GRNItemRepository extends CrudRepository<GRNItems, Long> {
}
