package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Brand;
import com.erp.mastro.model.request.BrandRequestModel;

import java.util.List;


public interface BrandService {

    List<Brand> getAllBrands();

    Brand getBrandId(Long id);

    void saveOrUpdateBrand(BrandRequestModel brandRequestModel);

    void deleteBrand(Long id);

    public void deleteBrandDetails(Long id);

}
