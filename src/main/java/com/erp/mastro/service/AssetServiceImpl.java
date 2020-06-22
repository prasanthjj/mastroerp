package com.erp.mastro.service;


import com.erp.mastro.dao.AssetRepository;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    public List<Assets> getAllAssets()
    {
        List<Assets> assetsList = new ArrayList<Assets>();
        assetRepository.findAll().forEach(assets -> assetsList.add(assets));
        return assetsList;
    }

    public Assets getAssetsById(Long id) {return assetRepository.findById(id).get();}

    public void saveOrUpdateAssets(Assets assets) {assetRepository.save(assets);}

    public  void deleteAssets(Long id) {assetRepository.deleteById(id);}

}
