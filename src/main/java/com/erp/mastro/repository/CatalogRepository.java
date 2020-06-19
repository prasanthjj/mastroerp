package com.erp.mastro.repository;

import com.erp.mastro.entities.Catalog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository  extends CrudRepository<Catalog, Long> {
}
