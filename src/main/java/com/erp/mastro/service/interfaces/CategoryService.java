package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

     List<Category> getAllCategories();

     Set<Category> getCatalogCategories(Catalog catalog);

     Category getCategoryById(Long id);

     void saveOrUpdateCategory(Category category);

     void deleteCategory(Long id);


}
