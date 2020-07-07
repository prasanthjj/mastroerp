package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.model.request.SubCategoryRequestModel;

import java.util.List;
import java.util.Set;

public interface SubcategoryService {

     List<SubCategory> getAllSubCategories();

     Set<SubCategory> getCategorySubCategory(Category category);

     SubCategory getSubCategoryById(Long id);

     void saveOrUpdateSubCategory(SubCategoryRequestModel subCategoryRequestModel);

     void deleteSubCategory(Long id);
}
