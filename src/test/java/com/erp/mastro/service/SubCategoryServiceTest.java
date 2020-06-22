package com.erp.mastro.service;

import com.erp.mastro.dao.CategoryRepository;
import com.erp.mastro.dao.SubCategoryRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SubCategoryServiceTest {

    @Autowired
    private SubcategoryService subcategoryService;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

    public List<SubCategory> addSubCategorys() {

        List<SubCategory> subCategories = new ArrayList<SubCategory>();
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        Stream<SubCategory> stream = Stream.of(new SubCategory(1L,"a11","a22",category),
                new SubCategory (2L,"b11","b22",category));
        subCategories= stream.collect(Collectors.toList());
        return subCategories;

    }

    @Test
    public void getSubCategorysSizeEqualTest() {
        when(subCategoryRepository.findAll()).thenReturn(addSubCategorys());
        Assert.assertEquals(2,subcategoryService.getAllSubCategories().size());
    }

    @Test
    public void getSubCategorysSizeNotEqualTest() {
        when(subCategoryRepository.findAll()).thenReturn(addSubCategorys());
        Assert.assertNotEquals(1,subcategoryService.getAllSubCategories().size());
    }

    @Test
    public void getByIdTest() {

        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(subCategory));
        Assert.assertEquals(subCategory,subcategoryService.getSubCategoryById(1L));

    }

    @Test
    public void saveSubCategoryTest()
    {
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        subcategoryService.saveOrUpdateSubCategory(subCategory);
        verify(subCategoryRepository, times(1)).save(subCategory);
    }
}
