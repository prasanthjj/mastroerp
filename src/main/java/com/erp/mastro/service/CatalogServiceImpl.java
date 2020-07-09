package com.erp.mastro.service;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.repository.CatalogRepository;
import com.erp.mastro.service.interfaces.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<Catalog> getAllCatalogs()
    {
        List<Catalog> catalogList = new ArrayList<Catalog>();
        catalogRepository.findAll().forEach(catalog -> catalogList.add(catalog));
        return catalogList;
    }

    public Catalog getCatalogById(Long id)
    {
        return catalogRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateCatalog(CatalogRequestModel catalogRequestModel) {
        if (catalogRequestModel.getId() == null) {
            Catalog catalog = new Catalog();
            catalog.setCatalogName(catalogRequestModel.getCatalogName());
            catalog.setCatalogDescription(catalogRequestModel.getCatalogDescription());
            catalogRepository.save(catalog);
        } else {
            Catalog catalog = getCatalogById(catalogRequestModel.getId());
            catalog.setCatalogName(catalogRequestModel.getCatalogName());
            catalog.setCatalogDescription(catalogRequestModel.getCatalogDescription());
            catalogRepository.save(catalog);
        }
    }

    public void deleteCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteCatalogDetails(Long id) {

        Catalog catalog = getCatalogById(id);
        catalog.setCatalogDeleteStatus(1);
        catalogRepository.save(catalog);

    }
}
