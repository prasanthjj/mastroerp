package com.erp.mastro.dao;

import com.erp.mastro.entities.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository  extends CrudRepository<SubCategory, Long> {
}
