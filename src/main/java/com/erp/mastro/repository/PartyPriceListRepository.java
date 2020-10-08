package com.erp.mastro.repository;

import com.erp.mastro.entities.PartyPriceList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyPriceListRepository extends CrudRepository<PartyPriceList, Long> {
}
