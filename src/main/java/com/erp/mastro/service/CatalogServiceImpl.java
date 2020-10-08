package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    /**
     * Method to list all catalogs
     *
     * @return catalog list
     */
    public List<Catalog> getAllCatalogs() {
        List<Catalog> catalogList = new ArrayList<Catalog>();
        catalogRepository.findAll().forEach(catalog -> catalogList.add(catalog));
        return catalogList;
    }

    /**
     * Method to get a catalog
     *
     * @param id
     * @return catalog
     */
    public Catalog getCatalogById(Long id) {
        Catalog catalog = new Catalog();
        if (id != null) {
            MastroLogUtils.info(CatalogService.class, "Going to getCatalogBy Id : {}" + id);
            catalog = catalogRepository.findById(id).get();
        }
        return catalog;
    }

    /**
     * Methos to save catalog
     *
     * @param catalogRequestModel
     * @return catalog
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Catalog saveOrUpdateCatalog(CatalogRequestModel catalogRequestModel) throws ModelNotFoundException {

        Catalog catalog = new Catalog();
        if (catalogRequestModel == null) {
            throw new ModelNotFoundException("catalogRequestModel is empty");
        } else {
            MastroLogUtils.info(CatalogService.class, "Going to save catalog  {}" + catalogRequestModel.toString());
            catalog.setCatalogName(catalogRequestModel.getCatalogName());
            catalog.setCatalogDescription(catalogRequestModel.getCatalogDescription());
            catalog.setCreationDate(new Date());
            catalogRepository.save(catalog);
            MastroLogUtils.info(CatalogService.class, "Added " + catalog.getCatalogName() + " succesfully.");
        }
        return catalog;

    }

    /**
     * Method to save and edit category
     *
     * @param categoryRequestModel
     * @return category
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Category saveOrUpdateCategory(CategoryRequestModel categoryRequestModel) throws ModelNotFoundException {

        Category category = new Category();
        if (categoryRequestModel == null) {
            throw new ModelNotFoundException("CategoryRequestModel is empty");
        } else {
            if (categoryRequestModel.getId() == null) {
                MastroLogUtils.info(CatalogService.class, "Going to save categeory  {}" + categoryRequestModel.toString());
                category.setCategoryName(categoryRequestModel.getCategoryName());
                category.setCategoryDescription(categoryRequestModel.getCategoryDescription());
                category.setCategoryShortCode(categoryRequestModel.getCategoryShortCode());
                category.setCategoryType(categoryRequestModel.getCategoryType());
                category.setCreationDate(new Date());
                category.setCatalog(getCatalogById(categoryRequestModel.getCatalogId()));
                categoryRepository.save(category);
                MastroLogUtils.info(CatalogService.class, "Added " + category.getCategoryName() + " succesfully.");
            } else {
                MastroLogUtils.info(CatalogService.class, "Going to edit categeory  {}" + categoryRequestModel.toString());
                category = categoryRepository.findById(categoryRequestModel.getId()).get();
                category.setCategoryName(categoryRequestModel.getCategoryName());
                category.setCategoryDescription(categoryRequestModel.getCategoryDescription());
                category.setCategoryShortCode(categoryRequestModel.getCategoryShortCode());
                category.setCategoryType(categoryRequestModel.getCategoryType());
                category.setCreationDate(category.getCreationDate());
                category.setCatalog(category.getCatalog());
                categoryRepository.save(category);
                MastroLogUtils.info(CatalogService.class, "Edited " + category.getCategoryName() + " succesfully.");
            }
        }
        return category;

    }

    public void deleteCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    /**
     * Method to delete category
     *
     * @param id
     * @return category
     */
    @Transactional(rollbackOn = {Exception.class})
    public Category deleteCategoryDetails(Long id) {
        Category category = new Category();
        if (id != null) {
            MastroLogUtils.info(CatalogService.class, "Going to delete categeory  {}" + id);
            category = getCategoryById(id);
            category.setCategoryDeleteStatus(1);
            categoryRepository.save(category);

        }
        return category;

    }

    /**
     * Method to get a category
     *
     * @param id
     * @return category
     */
    public Category getCategoryById(Long id) {

        Category category = new Category();
        if (id != null) {
            MastroLogUtils.info(CatalogService.class, "Going to getCategoryBy Id : {}" + id);
            category = categoryRepository.findById(id).get();
        }
        return category;
    }

    /**
     * Method to get a subcategory
     *
     * @param id
     * @return subcategory
     */
    public SubCategory getSubCategoryById(Long id) {

        SubCategory subCategory = new SubCategory();
        if (id != null) {
            MastroLogUtils.info(CatalogService.class, "Going to getSubCategoryBy Id : {}" + id);
            subCategory = subCategoryRepository.findById(id).get();
        }
        return subCategory;
    }

    /**
     * Method to save or update subcategory
     *
     * @param subCategoryRequestModel
     * @return subcategory
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public SubCategory saveOrUpdateSubCategory(SubCategoryRequestModel subCategoryRequestModel) throws ModelNotFoundException {

        SubCategory subCategory = new SubCategory();
        if (subCategoryRequestModel == null) {
            throw new ModelNotFoundException("SubCategoryRequestModel is empty");
        } else {
            if (subCategoryRequestModel.getId() == null) {
                MastroLogUtils.info(CatalogService.class, "Going to save subcategeory  {}" + subCategoryRequestModel.toString());
                subCategory.setSubCategoryName(subCategoryRequestModel.getSubCategoryName());
                subCategory.setSubCategoryDescription(subCategoryRequestModel.getSubCategoryDescription());
                subCategory.setCategory(getCategoryById(subCategoryRequestModel.getCategoryId()));
                subCategoryRepository.save(subCategory);
                MastroLogUtils.info(CatalogService.class, "Added " + subCategory.getSubCategoryName() + " succesfully.");
            } else {
                MastroLogUtils.info(CatalogService.class, "Going to edit subcategeory  {}" + subCategoryRequestModel.toString());
                subCategory = subCategoryRepository.findById(subCategoryRequestModel.getId()).get();
                subCategory.setSubCategoryName(subCategoryRequestModel.getSubCategoryName());
                subCategory.setSubCategoryDescription(subCategoryRequestModel.getSubCategoryDescription());
                subCategory.setCategory(subCategory.getCategory());
                subCategoryRepository.save(subCategory);
                MastroLogUtils.info(CatalogService.class, "Edited " + subCategory.getSubCategoryName() + " succesfully.");
            }
        }
        return subCategory;

    }

    /**
     * method to delete subcategory
     *
     * @param id
     * @return subcategory
     */
    @Transactional(rollbackOn = {Exception.class})
    public SubCategory deleteSubCategoryDetails(Long id) {
        SubCategory subCategory = new SubCategory();
        if (id != null) {
            MastroLogUtils.info(CatalogService.class, "Going to delete Subcategeory  {}" + id);
            subCategory = getSubCategoryById(id);
            subCategory.setSubCategoryDeleteStatus(1);
            subCategoryRepository.save(subCategory);

        }
        return subCategory;

    }
}
