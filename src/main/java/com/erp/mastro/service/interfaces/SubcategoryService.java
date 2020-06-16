package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;

import java.util.List;
import java.util.Set;

public interface SubcategoryService {

     List<SubCategory> getAllSubCategories();

     Set<SubCategory> getCategorySubCategory(Category category);

     SubCategory getSubCategoryById(Long id);

     void saveOrUpdateSubCategory(SubCategory subCategory);

     void deleteSubCategory(Long id);
}
