package com.erp.mastro.service;

import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.model.request.SubCategoryRequestModel;
import com.erp.mastro.repository.CategoryRepository;
import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
        subCategoryRepository.findAll().forEach(subCategory -> subCategoryList.add(subCategory));
        return subCategoryList;
    }

    public Set<SubCategory> getCategorySubCategory(Category category) {
        Set<SubCategory> subCategorySet = new HashSet<>();
        subCategorySet = category.getSubCategories();
        return subCategorySet;
    }

    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id).get();
    }

    public void saveOrUpdateSubCategory(SubCategoryRequestModel subCategoryRequestModel) {
        if (subCategoryRequestModel.getId() == null) {
            SubCategory subCategory = new SubCategory();
            subCategory.setSubCategoryName(subCategoryRequestModel.getSubCategoryName());
            subCategory.setSubCategoryDescription(subCategoryRequestModel.getSubCategoryDescription());
            Category category = categoryRepository.findById(subCategoryRequestModel.getCategoryId()).get();
            subCategory.setCategory(category);
            category.getSubCategories().add(subCategory);
            categoryRepository.save(category);
        } else {
            SubCategory subCategory = getSubCategoryById(subCategoryRequestModel.getId());
            subCategory.setSubCategoryName(subCategoryRequestModel.getSubCategoryName());
            subCategory.setSubCategoryDescription(subCategoryRequestModel.getSubCategoryDescription());
            subCategoryRepository.save(subCategory);
        }
    }

    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }
}
