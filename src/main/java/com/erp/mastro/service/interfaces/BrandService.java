package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

    List<Brand> getAllBrands();

    Brand getBrandId(Long id);

    void saveOrUpdateBrand(Brand brand);

    void deleteBrand(Long id);

}
