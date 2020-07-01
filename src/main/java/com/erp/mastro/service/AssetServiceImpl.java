package com.erp.mastro.service;


import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void saveOrUpdateAssets(AssetRequestModel assetRequestModel) {
        if (assetRequestModel.getId() == null) {
            Assets assets = new Assets();
            assets.setAssetNo(assetRequestModel.getAssetNo());
            assets.setAssetName(assetRequestModel.getAssetName());
            assets.setAssetType(assetRequestModel.getAssetType());
            assets.setAssetLocation(assetRequestModel.getAssetLocation());
            assets.setSubLocation(assetRequestModel.getSubLocation());
            assets.setPartyNo(assetRequestModel.getPartyNo());
            assets.setHoursUtilized(assetRequestModel.getHoursUtilized());
            assets.setInstallationDate(assetRequestModel.getInstallationDate());
            assets.setEffectiveDate(assetRequestModel.getEffectiveDate());
            assets.setCapacity(assetRequestModel.getCapacity());
            assets.setMaintenancePriority(assetRequestModel.getMaintenancePriority());
            assets.setActive(assetRequestModel.isActive());
            assets.setMaintenanceRequired(assetRequestModel.isMaintenanceRequired());
            assets.setMake(assetRequestModel.getMake());
            assetRepository.save(assets);
        } else {
            Assets assets = assetRepository.findById(assetRequestModel.getId()).get();
            assets.setAssetNo(assetRequestModel.getAssetNo());
            assets.setAssetName(assetRequestModel.getAssetName());
            assets.setAssetType(assetRequestModel.getAssetType());
            assets.setAssetLocation(assetRequestModel.getAssetLocation());
            assets.setSubLocation(assetRequestModel.getSubLocation());
            assets.setPartyNo(assetRequestModel.getPartyNo());
            assets.setHoursUtilized(assetRequestModel.getHoursUtilized());
            assets.setInstallationDate(assetRequestModel.getInstallationDate());
            assets.setEffectiveDate(assetRequestModel.getEffectiveDate());
            assets.setCapacity(assetRequestModel.getCapacity());
            assets.setMaintenancePriority(assetRequestModel.getMaintenancePriority());
            assets.setActive(assetRequestModel.isActive());
            assets.setMaintenanceRequired(assetRequestModel.isMaintenanceRequired());
            assets.setMake(assetRequestModel.getMake());
            assetRepository.save(assets);
        }
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
