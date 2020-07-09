package com.erp.mastro.service;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.model.request.CategoryRequestModel;
import com.erp.mastro.repository.CatalogRepository;
import com.erp.mastro.repository.CategoryRepository;
import com.erp.mastro.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        categoryRepository.findAll().forEach(category -> categoryList.add(category));
        return categoryList;
    }

    public Set<Category> getCatalogCategories(Catalog catalog) {
        Set<Category> categorySet = new HashSet<>();
        categorySet=catalog.getCategories();
        return categorySet;
    }

    public Category getCategoryById(Long id)
    {
        return categoryRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateCategory(CategoryRequestModel categoryRequestModel) {
        if (categoryRequestModel.getId() == null) {
            Category category = new Category();
            category.setCategoryName(categoryRequestModel.getCategoryName());
            category.setCategoryDescription(categoryRequestModel.getCategoryDescription());
            category.setCategoryShortCode(categoryRequestModel.getCategoryShortCode());
            category.setCategoryType(categoryRequestModel.getCategoryType());
            Catalog catalog = catalogRepository.findById(categoryRequestModel.getCatalogId()).get();
            category.setCatalog(catalog);
            catalog.getCategories().add(category);
            catalogRepository.save(catalog);
        } else {
            Category category = getCategoryById(categoryRequestModel.getId());
            category.setCategoryName(categoryRequestModel.getCategoryName());
            category.setCategoryDescription(categoryRequestModel.getCategoryDescription());
            category.setCategoryShortCode(categoryRequestModel.getCategoryShortCode());
            category.setCategoryType(categoryRequestModel.getCategoryType());
            categoryRepository.save(category);
        }
    }

    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }
}
