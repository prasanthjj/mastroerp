package com.erp.mastro.service;

import com.erp.mastro.dao.CatalogRepository;
import com.erp.mastro.dao.CategoryRepository;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories()
    {
        List<Category> categoryList = new ArrayList<Category>();
        categoryRepository.findAll().forEach(category ->categoryList.add(category));
        return categoryList;
    }

    public Set<Category> getCatalogCategories(Catalog catalog)
    {
        Set<Category> categorySet = new HashSet<>();
        categorySet=catalog.getCategories();
        return categorySet;
    }

    public Category getCategoryById(Long id)
    {
        return categoryRepository.findById(id).get();
    }

    public void saveOrUpdateCategory(Category category)
    {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id)
    {
        categoryRepository.deleteById(id);
    }
}
