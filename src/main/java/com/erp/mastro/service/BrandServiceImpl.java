package com.erp.mastro.service;

import com.erp.mastro.entities.Brand;
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
    public void saveOrUpdateBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

}
