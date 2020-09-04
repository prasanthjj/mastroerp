package com.erp.mastro.repository;

import com.erp.mastro.entities.IndentItemPartyGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndentItemPartyGroupRepository extends CrudRepository<IndentItemPartyGroup, Long> {
}
