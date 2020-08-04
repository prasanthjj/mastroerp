package com.erp.mastro.service;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.model.request.CategoryRequestModel;
import com.erp.mastro.model.request.SubCategoryRequestModel;
import com.erp.mastro.repository.CatalogRepository;
import com.erp.mastro.repository.CategoryRepository;
import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.service.interfaces.CatalogService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CatalogServiceTest {

    @Autowired
    private CatalogService catalogService;

    @MockBean
    private CatalogRepository catalogRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

    public CatalogRequestModel catalogModel() {

        CatalogRequestModel catalogRequestModel = new CatalogRequestModel();
        catalogRequestModel.setCatalogName("sheet");
        catalogRequestModel.setCatalogDescription("roofingsheets");

        return catalogRequestModel;
    }

    public Catalog getCatalog() {
        Catalog catalog = new Catalog(1L, "sheet", "roofingsheets");
        return catalog;
    }

    @Test
    public void testSaveOrUpdateCatalog() throws ModelNotFoundException {

        Catalog catalog = catalogService.saveOrUpdateCatalog(catalogModel());
        Assert.assertEquals("sheet", catalog.getCatalogName());
        Assert.assertEquals("roofingsheets", catalog.getCatalogDescription());

    }

    @Test
    public void testCatalogModelNull() {

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                catalogService.saveOrUpdateCatalog(null))
                .isExactlyInstanceOf(ModelNotFoundException.class);

    }

    @Test
    public void testCatalogGetById() {

        when(catalogRepository.findById(1L)).thenReturn(Optional.of(getCatalog()));
        Assert.assertEquals(getCatalog().getId(), catalogService.getCatalogById(getCatalog().getId()).getId());
    }

    public CategoryRequestModel categoryModel() {

        CategoryRequestModel categoryRequestModel = new CategoryRequestModel();
        categoryRequestModel.setCategoryName("category1");
        categoryRequestModel.setCategoryDescription("desccategory");
        categoryRequestModel.setCategoryShortCode("cat11");
        categoryRequestModel.setCategoryType("a");
        categoryRequestModel.setCatalogId(1L);

        return categoryRequestModel;
    }

    public Category getCategory() {
        Category category = new Category(1L, "category1", "desccategory", "cat11", "a", getCatalog());
        return category;
    }

    @Test
    public void testSaveorUpdateCategory() throws ModelNotFoundException {

        when(catalogRepository.findById(1L)).thenReturn(Optional.of(getCatalog()));
        Category category = catalogService.saveOrUpdateCategory(categoryModel());
        Assert.assertEquals("category1", category.getCategoryName());
        Assert.assertEquals("desccategory", category.getCategoryDescription());
        Assert.assertEquals("cat11", category.getCategoryShortCode());
        Assert.assertEquals("a", category.getCategoryType());

    }

    @Test
    public void testCategoryModelNull() {

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                catalogService.saveOrUpdateCategory(null))
                .isExactlyInstanceOf(ModelNotFoundException.class);

    }

    @Test
    public void testCategoryGetById() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getCategory()));
        Assert.assertEquals(getCategory().getId(), catalogService.getCategoryById(getCategory().getId()).getId());
    }

    @Test
    public void testDeleteCategory() {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getCategory()));
        Category category = catalogService.deleteCategoryDetails(1L);
        Assert.assertEquals(1, category.getCategoryDeleteStatus());
    }

    public SubCategoryRequestModel subCategoryModel() {

        SubCategoryRequestModel subCategoryRequestModel = new SubCategoryRequestModel();
        subCategoryRequestModel.setSubCategoryName("sub1");
        subCategoryRequestModel.setSubCategoryDescription("sub1desc");
        subCategoryRequestModel.setCategoryId(1L);

        return subCategoryRequestModel;
    }

    public SubCategory getSubCategory() {
        SubCategory subCategory = new SubCategory(1L, "sub1", "sub1desc", getCategory());
        return subCategory;
    }

    @Test
    public void testSaveOrUpdateSubCategory() throws ModelNotFoundException {

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(getCategory()));
        SubCategory subCategory = catalogService.saveOrUpdateSubCategory(subCategoryModel());
        Assert.assertEquals("sub1", subCategory.getSubCategoryName());
        Assert.assertEquals("sub1desc", subCategory.getSubCategoryDescription());

    }

    @Test
    public void testSubCategoryModelNull() {

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                catalogService.saveOrUpdateSubCategory(null))
                .isExactlyInstanceOf(ModelNotFoundException.class);

    }

    @Test
    public void testSubCategoryGetById() {

        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(getSubCategory()));
        Assert.assertEquals(getSubCategory().getId(), catalogService.getSubCategoryById(getSubCategory().getId()).getId());
    }

    @Test
    public void testDeleteSubCategory() {

        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(getSubCategory()));
        SubCategory subCategory = catalogService.deleteSubCategoryDetails(1L);
        Assert.assertEquals(1, subCategory.getSubCategoryDeleteStatus());
    }

}
