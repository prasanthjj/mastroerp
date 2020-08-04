package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.model.request.CategoryRequestModel;
import com.erp.mastro.model.request.SubCategoryRequestModel;
import com.erp.mastro.service.interfaces.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master")
/**
 * catalog controller
 */
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    /**
     * method to get all catalogs
     *
     * @param model
     * @return all catalogs
     */
    @GetMapping("/getCatalog")
    public String getCatalog(Model model) {

        try {
            MastroLogUtils.info(CatalogController.class, "List catalog : {}");
            List<Catalog> catalogList = catalogService.getAllCatalogs();
            model.addAttribute("catalogForm", new CatalogRequestModel());
            model.addAttribute("categoryForm", new CategoryRequestModel());
            model.addAttribute("subCategoryForm", new SubCategoryRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("catalogTab", "catalog");
            model.addAttribute("catalogList", catalogList);
            return "views/catalogMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * method to Save catalog
     *
     * @param catalogRequestModel
     * @param request
     * @param model
     * @return catalog list
     */
    @PostMapping("/saveCatalog")
    public String saveCatalog(@ModelAttribute("catalogForm") @Valid CatalogRequestModel catalogRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(CatalogController.class, "Going to save catalog : {}");
        try {
            catalogService.saveOrUpdateCatalog(catalogRequestModel);
            return "redirect:/master/getCatalog";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "CatalogRequestModel empty", e);
            return "redirect:/master/getCatalog";
        } catch (Exception e) {
            MastroLogUtils.error(CatalogController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * Method to save category
     *
     * @param categoryRequestModel
     * @param request
     * @param model
     * @return catalog structure
     */
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("categoryForm") @Valid CategoryRequestModel categoryRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(CatalogController.class, "Going to save category : {}");
        try {
            catalogService.saveOrUpdateCategory(categoryRequestModel);
            return "redirect:/master/getCatalog";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "CategoryRequestModel empty", e);
            return "redirect:/master/getCatalog";
        } catch (Exception e) {
            MastroLogUtils.error(CatalogController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * Method to get category for edit
     *
     * @param model
     * @param request
     * @param categoryId
     * @return category details in response
     */
    @GetMapping("/getCategoryForEdit")
    @ResponseBody
    public GenericResponse getCategoryForEdit(Model model, HttpServletRequest request, @RequestParam("categoryId") Long categoryId) {

        try {
            if (categoryId != null) {
                MastroLogUtils.info(CatalogController.class, "Going to edit category Details : {}" + categoryId);
                Category category = catalogService.getCategoryById(categoryId);
                return new GenericResponse(true, "get category details")
                        .setProperty("categoryId", category.getId())
                        .setProperty("categoryShortCode", category.getCategoryShortCode())
                        .setProperty("categoryType", category.getCategoryType())
                        .setProperty("categoryName", category.getCategoryName())
                        .setProperty("categoryDescription", category.getCategoryDescription());
            } else {
                MastroLogUtils.info(CatalogController.class, "category id null");
                return new GenericResponse(false, "category id null");
            }

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while editing category details :{}", e);
            throw e;

        }

    }

    /**
     * Method to save and edit subcategory
     *
     * @param subCategoryRequestModel
     * @param request
     * @param model
     * @return catalog list
     */
    @PostMapping("/saveSubCategory")
    public String saveSubCategory(@ModelAttribute("subCategoryForm") @Valid SubCategoryRequestModel subCategoryRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(CatalogController.class, "Going to save sub category : {}");
        try {
            catalogService.saveOrUpdateSubCategory(subCategoryRequestModel);
            return "redirect:/master/getCatalog";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "SubCategoryRequestModel empty", e);
            return "redirect:/master/getCatalog";
        } catch (Exception e) {
            MastroLogUtils.error(CatalogController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * Method to get subcategory details for edit
     *
     * @param model
     * @param request
     * @param subCategoryId
     * @return the subcategory details in response
     */
    @GetMapping("/getSubCategoryForEdit")
    @ResponseBody
    public GenericResponse getSubCategoryForEdit(Model model, HttpServletRequest request, @RequestParam("subCategoryId") Long subCategoryId) {

        try {
            if (subCategoryId != null) {
                MastroLogUtils.info(CatalogController.class, "Going to edit subcategory Details : {}" + subCategoryId);
                SubCategory subCategory = catalogService.getSubCategoryById(subCategoryId);
                return new GenericResponse(true, "get subcategory details")
                        .setProperty("subCategoryId", subCategory.getId())
                        .setProperty("subCategoryName", subCategory.getSubCategoryName())
                        .setProperty("subCategoryDescription", subCategory.getSubCategoryDescription());
            } else {
                MastroLogUtils.info(CatalogController.class, "subcategory id null");
                return new GenericResponse(false, "subcategory id null");
            }

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while editing subcategory details :{}", e);
            throw e;

        }

    }

    /**
     * Method to delete category
     *
     * @param model
     * @param request
     * @param categoryId
     * @return the result in response
     */
    @PostMapping("/deleteCategoryDetails")
    @ResponseBody
    public GenericResponse deleteCategoryDetails(Model model, HttpServletRequest request, @RequestParam("categoryid") Long categoryId) {

        try {
            if (categoryId != null) {
                MastroLogUtils.info(CatalogController.class, "Going to delete category : {}" + categoryId);
                catalogService.deleteCategoryDetails(categoryId);
                return new GenericResponse(true, "delete category details");
            } else {
                MastroLogUtils.info(CatalogController.class, "category id null");
                return new GenericResponse(false, "category id null");
            }

        } catch (Exception e) {
            MastroLogUtils.error(CatalogController.class, "Error occured while deleting category : {}", e);
            return new GenericResponse(false, e.getMessage());

        }

    }

    /**
     * Method to delete subcategory
     *
     * @param model
     * @param request
     * @param subCategoryId
     * @return the result in response
     */
    @PostMapping("/deleteSubCategoryDetails")
    @ResponseBody
    public GenericResponse deleteSubCategoryDetails(Model model, HttpServletRequest request, @RequestParam("subCategoryid") Long subCategoryId) {

        try {
            if (subCategoryId != null) {
                MastroLogUtils.info(CatalogController.class, "Going to delete subcategory : {}" + subCategoryId);
                catalogService.deleteSubCategoryDetails(subCategoryId);
                return new GenericResponse(true, "delete subcategory details");
            } else {
                MastroLogUtils.info(CatalogController.class, "subcategory id null");
                return new GenericResponse(false, "subcategory id null");
            }

        } catch (Exception e) {
            MastroLogUtils.error(CatalogController.class, "Error occured while deleting subcategory : {}", e);
            return new GenericResponse(false, e.getMessage());

        }

    }

}
