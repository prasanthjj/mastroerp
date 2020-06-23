package com.erp.mastro.repository;

import com.erp.mastro.entities.ProductPartyRateRelation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRateRepository extends CrudRepository<ProductPartyRateRelation,Long> {
}
