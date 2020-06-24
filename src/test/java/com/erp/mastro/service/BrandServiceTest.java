package com.erp.mastro.service;

import com.erp.mastro.entities.Brand;
import com.erp.mastro.repository.BrandRepository;
import com.erp.mastro.service.interfaces.BrandService;
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
public class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @MockBean
    private BrandRepository brandRepository;

    public List<Brand> addBrand() {
        List<Brand> brand = new ArrayList<Brand>();
        Stream<Brand> stream = Stream.of(new Brand(1L, "Tata", "Tata metals"),
                new Brand(2L, "Pvc", "pvc pipes"));
        brand = stream.collect(Collectors.toList());
        return brand;
    }
    public Brand addBrands(){
        Brand brands = new Brand(3L,"a","b");
        return brands;
    }
    @Test
    public void testGetBrandSizeEqual() {
        when(brandRepository.findAll()).thenReturn(addBrand());
        Assert.assertEquals(2,brandService.getAllBrands().size());
    }
    @Test
    public void testGetBrandSizeNotEqual(){
        when(brandRepository.findAll()).thenReturn(addBrand());
        Assert.assertNotEquals(1,brandService.getAllBrands().size());
    }
    @Test
    public void testGetById() {

        when(brandRepository.findById(3L)).thenReturn(Optional.of(addBrands()));
        Assert.assertEquals(addBrands().getId(),brandService.getBrandId(addBrands().getId()).getId());
    }
    @Test
    public void testSaveBrand(){
        Brand brand = new Brand(3L,"AMC","Amc Pipes");
        brandService.saveOrUpdateBrand(brand);
        verify(brandRepository,times(1)).save(brand);
    }
    @Test
    public void testDeletBrand(){
       brandService.deleteBrand(addBrands().getId());
       verify(brandRepository,times(1)).deleteById(addBrands().getId());
    }
    @Test
    public void testGetBrandValidationSuccess(){
        when(brandRepository.findById(addBrands().getId())).thenReturn(Optional.of(addBrands()));
        Assert.assertEquals("a",brandService.getBrandId(addBrands().getId()).getBrandName());
        Assert.assertEquals("b",brandService.getBrandId(addBrands().getId()).getBrandDescription());

    }
}
