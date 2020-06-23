package com.erp.mastro.service;

import com.erp.mastro.dao.CatalogRepository;
import com.erp.mastro.repository.CategoryRepository;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.service.interfaces.CatalogService;
import com.erp.mastro.service.interfaces.CategoryService;
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
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    public List<Category> addCategorys() {

        List<Category> categorys = new ArrayList<Category>();
        Catalog catalog = new Catalog(3L, "A ","b");
        Stream<Category> stream = Stream.of(new Category(1L,"a1","a2","a3","a4",catalog),
                new Category(2L,"b1","b2","b3","b4",catalog));
        categorys= stream.collect(Collectors.toList());
        return categorys;

    }

    public Category addCategory() {
        Catalog catalog = new Catalog(3L, "a ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        return category;
    }

    @Test
    public void testGetCategorysSizeEqual() {
        when(categoryRepository.findAll()).thenReturn(addCategorys());
        Assert.assertEquals(2,categoryService.getAllCategories().size());
    }

    @Test
    public void testGetCategorysSizeNotEqual() {

        when(categoryRepository.findAll()).thenReturn(addCategorys());
        Assert.assertNotEquals(1,categoryService.getAllCategories().size());

    }

    @Test
    public void testGetById() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(addCategory()));
        Assert.assertEquals(addCategory().getId(),categoryService.getCategoryById(addCategory().getId()).getId());

    }


    @Test
    public void testSaveCategory()
    {
        Catalog catalog = new Catalog(3L, "a ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        categoryService.saveOrUpdateCategory(category);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {

        categoryService.deleteCategory(addCategory().getId());
        verify(categoryRepository,times(1)).deleteById(addCategory().getId());

    }

    @Test
    public void testGetCategoryValidationSucess() {

        when(categoryRepository.findById(addCategory().getId())).thenReturn(Optional.of(addCategory()));
        Assert.assertEquals("a1",categoryService.getCategoryById(addCategory().getId()).getCategoryName());
        Assert.assertEquals("a2",categoryService.getCategoryById(addCategory().getId()).getCategoryDescription());
        Assert.assertEquals("a3",categoryService.getCategoryById(addCategory().getId()).getCategoryShortCode());
        Assert.assertEquals("a4",categoryService.getCategoryById(addCategory().getId()).getCategoryType());
        Assert.assertEquals(addCategory().getCatalog().getId(),categoryService.getCategoryById(addCategory().getId()).getCatalog().getId());
    }

}
