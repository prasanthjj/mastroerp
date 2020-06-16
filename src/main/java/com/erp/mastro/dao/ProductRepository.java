package com.erp.mastro.dao;

import com.erp.mastro.entities.Location;
import com.erp.mastro.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
