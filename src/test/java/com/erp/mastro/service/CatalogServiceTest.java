package com.erp.mastro.service;

import com.erp.mastro.repository.CatalogRepository;
import com.erp.mastro.dao.LocationRepository;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Location;
import com.erp.mastro.service.interfaces.CatalogService;
import com.erp.mastro.service.interfaces.LocationService;
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
public class CatalogServiceTest {

    @Autowired
    private CatalogService catalogService;

    @MockBean
    private CatalogRepository catalogRepository;

    public List<Catalog> addCatalogs() {

        List<Catalog> catalogs = new ArrayList<Catalog>();
        Stream<Catalog> stream = Stream.of(new Catalog(1L,"Metals","metals catalog"),
                new Catalog (2L,"Electronics","electronics catalog"));
        catalogs = stream.collect(Collectors.toList());
        return catalogs;

    }

    public Catalog addCatalog () {
        Catalog catalog = new Catalog(3L, "a","b");
        return catalog;
    }

    @Test
    public void testGetCatalogsSizeEqual() {
        when(catalogRepository.findAll()).thenReturn(addCatalogs());
        Assert.assertEquals(2,catalogService.getAllCatalogs().size());
    }

    @Test
    public void testGetCatalogsSizeNotEqual() {
        when(catalogRepository.findAll()).thenReturn(addCatalogs());
        Assert.assertNotEquals(1,catalogService.getAllCatalogs().size());
    }


    @Test
    public void testGetById() {

        when(catalogRepository.findById(3L)).thenReturn(Optional.of(addCatalog()));
        Assert.assertEquals(addCatalog().getId(),catalogService.getCatalogById(addCatalog().getId()).getId());

    }
    @Test
    public void testSaveCatalog()
    {
        Catalog catalog = new Catalog(3L, "a","b");
        catalogService.saveOrUpdateCatalog(catalog);
        verify(catalogRepository, times(1)).save(catalog);
    }

    @Test
    public void testDeleteCatalog() {
        catalogService.deleteCatalog(addCatalog().getId());
        verify(catalogRepository,times(1)).deleteById(addCatalog().getId());
    }

    @Test
    public void testGetCatalogValidationSucess() {

        when(catalogRepository.findById(addCatalog().getId())).thenReturn(Optional.of(addCatalog()));
        Assert.assertEquals("a",catalogService.getCatalogById(addCatalog().getId()).getCatalogName());
        Assert.assertEquals("b",catalogService.getCatalogById(addCatalog().getId()).getCatalogDescription());
    }

}
