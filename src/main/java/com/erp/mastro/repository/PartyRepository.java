package com.erp.mastro.repository;

import com.erp.mastro.entities.Party;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends CrudRepository<Party,Long> {
}
