package com.erp.mastro.service;


import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    public List<Assets> getAllAssets() {
        List<Assets> assetsList = new ArrayList<Assets>();
        assetRepository.findAll().forEach(assets -> assetsList.add(assets));
        return assetsList;
    }

    public Assets getAssetsById(Long id) {
        return assetRepository.findById(id).get();
    }

    public void saveOrUpdateAssets(Assets assets) {
        assetRepository.save(assets);
    }

    public void saveOrUpdateAssetCharacteristics(Assets assets, AssetCharacteristics assetCharacteristics) {
        assets.setAssetCharacteristics(assetCharacteristics);
        assetRepository.save(assets);
    }

    public void saveOrUpdateAssetMaintenanceActivities(Assets assets, AssetMaintenanceActivities assetMaintenanceActivities) {
        assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
        assetRepository.save(assets);
    }

    public void saveOrUpdateAssetChecklist(Assets assets, AssetChecklist assetChecklist) {
        assets.setAssetChecklist(assetChecklist);
        assetRepository.save(assets);
    }

    public void deleteAssets(Long id) {
        assetRepository.deleteById(id);
    }


}
