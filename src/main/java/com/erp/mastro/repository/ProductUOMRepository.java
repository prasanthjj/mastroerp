package com.erp.mastro.repository;

import com.erp.mastro.entities.ProductUOM;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUOMRepository extends CrudRepository<ProductUOM, Long> {
}
