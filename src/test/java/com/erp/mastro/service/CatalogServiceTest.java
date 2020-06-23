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

    @Test
    public void getCatalogsSizeEqualTest() {
        when(catalogRepository.findAll()).thenReturn(addCatalogs());
        Assert.assertEquals(2,catalogService.getAllCatalogs().size());
    }

    @Test
    public void getCatalogsSizeNotEqualTest() {
        when(catalogRepository.findAll()).thenReturn(addCatalogs());
        Assert.assertNotEquals(1,catalogService.getAllCatalogs().size());
    }


}
