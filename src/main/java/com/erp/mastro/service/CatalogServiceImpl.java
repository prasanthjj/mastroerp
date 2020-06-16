package com.erp.mastro.service;

import com.erp.mastro.dao.CatalogRepository;
import com.erp.mastro.entities.Catalog;

import com.erp.mastro.service.interfaces.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveOrUpdateCatalog(Catalog catalog)
    {
        catalogRepository.save(catalog);
    }

    public void deleteCatalog(Long id)
    {
        catalogRepository.deleteById(id);
    }
}
