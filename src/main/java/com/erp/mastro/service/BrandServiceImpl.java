package com.erp.mastro.service;

import com.erp.mastro.entities.Brand;
import com.erp.mastro.model.request.BrandRequestModel;
import com.erp.mastro.repository.BrandRepository;
import com.erp.mastro.service.interfaces.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brandList = new ArrayList<Brand>();
        brandRepository.findAll().forEach(brand -> brandList.add(brand));
        return brandList;
    }

    @Override
    public Brand getBrandId(Long id) {
        return brandRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdateBrand(BrandRequestModel brandRequestModel) {

        if (brandRequestModel.getId() == null) {
            Brand brand = new Brand();
            brand.setBrandName(brandRequestModel.getBrandName());
            brand.setBrandDescription(brandRequestModel.getBrandDescription());
            brandRepository.save(brand);
        } else {
            Brand brand = brandRepository.findById(brandRequestModel.getId()).get();
            brand.setBrandName(brandRequestModel.getBrandName());
            brand.setBrandDescription(brandRequestModel.getBrandDescription());
            brandRepository.save(brand);
        }

    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public void deleteBrandDetails(Long id) {

        Brand brand = getBrandId(id);
        brand.setBrandDeleteStatus(1);
        brandRepository.save(brand);

    }


}
