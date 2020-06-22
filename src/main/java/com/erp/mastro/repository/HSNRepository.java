package com.erp.mastro.repository;


import com.erp.mastro.entities.HSN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HSNRepository extends CrudRepository<HSN, Long> {
}
