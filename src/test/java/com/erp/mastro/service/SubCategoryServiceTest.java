package com.erp.mastro.service;

import com.erp.mastro.dao.CategoryRepository;
import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.service.interfaces.CategoryService;
import com.erp.mastro.service.interfaces.SubcategoryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SubCategoryServiceTest {

    @Autowired
    private SubcategoryService subcategoryService;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

    public Set<SubCategory> addSubCategorys() {

        Set<SubCategory> subCategories = new HashSet<>();
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        Stream<SubCategory> stream = Stream.of(new SubCategory(1L,"a11","a22",category),
                new SubCategory (2L,"b11","b22",category));
        subCategories= stream.collect(Collectors.toSet());
        return subCategories;

    }

    public SubCategory addSubCategory() {

        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        return subCategory;

    }

    @Test
    public void testGetSubCategorysSizeEqual() {
        when(subCategoryRepository.findAll()).thenReturn(addSubCategorys());
        Assert.assertEquals(2,subcategoryService.getAllSubCategories().size());
    }

    @Test
    public void testGetSubCategorysSizeNotEqual() {
        when(subCategoryRepository.findAll()).thenReturn(addSubCategorys());
        Assert.assertNotEquals(1,subcategoryService.getAllSubCategories().size());
    }

    @Test
    public void testGetById() {

        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(addSubCategory()));
        Assert.assertEquals(addSubCategory().getId(),subcategoryService.getSubCategoryById(addSubCategory().getId()).getId());

    }

    @Test
    public void testSaveSubCategory()
    {
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        subcategoryService.saveOrUpdateSubCategory(subCategory);
        verify(subCategoryRepository, times(1)).save(subCategory);
    }

    @Test
    public void testDeleteSubCategory() {

        subcategoryService.deleteSubCategory(addSubCategory().getId());
        verify(subCategoryRepository,times(1)).deleteById(addSubCategory().getId());

    }

    @Test
    public void testSubCategoryValidationSucess() {

        when(subCategoryRepository.findById(addSubCategory().getId())).thenReturn(Optional.of(addSubCategory()));
        Assert.assertEquals("a11",subcategoryService.getSubCategoryById(addSubCategory().getId()).getSubCategoryName());
        Assert.assertEquals("a22",subcategoryService.getSubCategoryById(addSubCategory().getId()).getSubCategoryDescription());
        Assert.assertEquals(addSubCategory().getCategory().getId(),subcategoryService.getSubCategoryById(addSubCategory().getId()).getCategory().getId());
    }

    @Test
    public void testCategorySubCategorysSizeEqual() {

        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        Set<SubCategory> subCategorySet=new HashSet<>();
        subCategorySet=addSubCategorys();
        category.setSubCategories(subCategorySet);
        Assert.assertEquals( category.getSubCategories().size(),subcategoryService.getCategorySubCategory(category).size());

    }


}
