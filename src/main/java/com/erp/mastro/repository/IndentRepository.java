package com.erp.mastro.repository;

import com.erp.mastro.entities.Indent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndentRepository extends CrudRepository<Indent, Long> {
}
