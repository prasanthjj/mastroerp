package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.model.request.CategoryRequestModel;
import com.erp.mastro.model.request.SubCategoryRequestModel;

import java.util.List;

public interface CatalogService {

    List<Catalog> getAllCatalogs();

    Catalog getCatalogById(Long id);

    Catalog saveOrUpdateCatalog(CatalogRequestModel catalogRequestModel) throws ModelNotFoundException;

    Category saveOrUpdateCategory(CategoryRequestModel categoryRequestModel) throws ModelNotFoundException;

    void deleteCatalog(Long id);

    Category deleteCategoryDetails(Long id);

    Category getCategoryById(Long id);

    SubCategory saveOrUpdateSubCategory(SubCategoryRequestModel subCategoryRequestModel) throws ModelNotFoundException;

    SubCategory getSubCategoryById(Long id);

    SubCategory deleteSubCategoryDetails(Long id);

}
