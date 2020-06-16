package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Catalog;

import java.util.List;

public interface CatalogService {

     List<Catalog> getAllCatalogs();

     Catalog getCatalogById(Long id);

     void saveOrUpdateCatalog(Catalog catalog);

     void deleteCatalog(Long id);

}
