package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Brand;
import com.erp.mastro.model.request.BrandRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

    List<Brand> getAllBrands();

    Brand getBrandId(Long id);

    void saveOrUpdateBrand(BrandRequestModel brandRequestModel);

    void deleteBrand(Long id);

}
