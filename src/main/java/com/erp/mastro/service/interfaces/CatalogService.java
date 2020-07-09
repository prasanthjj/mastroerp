package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.model.request.CatalogRequestModel;

import java.util.List;

public interface CatalogService {

    List<Catalog> getAllCatalogs();

    Catalog getCatalogById(Long id);

    void saveOrUpdateCatalog(CatalogRequestModel catalogRequestModel);

    void deleteCatalog(Long id);

    void deleteCatalogDetails(Long id);

}
