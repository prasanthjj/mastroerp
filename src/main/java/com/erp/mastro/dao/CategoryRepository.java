package com.erp.mastro.dao;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends CrudRepository<Category, Long> {
}
