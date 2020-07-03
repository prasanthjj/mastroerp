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
            AssetCharacteristics assetCharacteristics = saveOrUpdateAssetCharacteristics(assetRequestModel, assets);
            assets.setAssetCharacteristics(assetCharacteristics);
            AssetMaintenanceActivities assetMaintenanceActivities = saveOrUpdateAssetMaintenanceActivities(assetRequestModel, assets);
            assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
            AssetChecklist assetChecklist = saveOrUpdateAssetChecklist(assetRequestModel, assets);
            assets.setAssetChecklist(assetChecklist);
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
            AssetCharacteristics assetCharacteristics = saveOrUpdateAssetCharacteristics(assetRequestModel, assets);
            assets.setAssetCharacteristics(assetCharacteristics);
            AssetMaintenanceActivities assetMaintenanceActivities = saveOrUpdateAssetMaintenanceActivities(assetRequestModel, assets);
            assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
            AssetChecklist assetChecklist = saveOrUpdateAssetChecklist(assetRequestModel, assets);
            assets.setAssetChecklist(assetChecklist);
            assetRepository.save(assets);
        }
    }

    public AssetCharacteristics saveOrUpdateAssetCharacteristics(AssetRequestModel assetRequestModel, Assets assets) {
        if (assets.getAssetCharacteristics() == null) {
            AssetCharacteristics assetCharacteristics = new AssetCharacteristics();
            assetCharacteristics.setAssets(assets);
            assetCharacteristics.setAssetRemarks(assetRequestModel.getAssetRemarks());
            assetCharacteristics.setValue(assetRequestModel.getValue());
            assetCharacteristics.setCharacter(assetRequestModel.getCharacter());
            return assetCharacteristics;
        } else {
            AssetCharacteristics assetCharacteristics = assets.getAssetCharacteristics();
            assetCharacteristics.setAssets(assets);
            assetCharacteristics.setAssetRemarks(assetRequestModel.getAssetRemarks());
            assetCharacteristics.setValue(assetRequestModel.getValue());
            assetCharacteristics.setCharacter(assetRequestModel.getCharacter());
            return assetCharacteristics;

        }
    }

    public AssetMaintenanceActivities saveOrUpdateAssetMaintenanceActivities(AssetRequestModel assetRequestModel, Assets assets) {
        if (assets.getAssetMaintenanceActivities() == null) {
            AssetMaintenanceActivities assetMaintenanceActivities = new AssetMaintenanceActivities();
            assetMaintenanceActivities.setAssets(assets);
            assetMaintenanceActivities.setActivityName(assetRequestModel.getActivityName());
            assetMaintenanceActivities.setCategory(assetRequestModel.getCategory());
            assetMaintenanceActivities.setFrequency(assetRequestModel.getFrequency());
            assetMaintenanceActivities.setStandardObservation(assetRequestModel.getStandardObservation());
            assetMaintenanceActivities.setUpperLimit(assetRequestModel.getUpperLimit());
            assetMaintenanceActivities.setTolerenceLowerlimit(assetRequestModel.getTolerenceLowerlimit());
            return assetMaintenanceActivities;
        } else {
            AssetMaintenanceActivities assetMaintenanceActivities = assets.getAssetMaintenanceActivities();
            assetMaintenanceActivities.setAssets(assets);
            assetMaintenanceActivities.setActivityName(assetRequestModel.getActivityName());
            assetMaintenanceActivities.setCategory(assetRequestModel.getCategory());
            assetMaintenanceActivities.setFrequency(assetRequestModel.getFrequency());
            assetMaintenanceActivities.setStandardObservation(assetRequestModel.getStandardObservation());
            assetMaintenanceActivities.setUpperLimit(assetRequestModel.getUpperLimit());
            assetMaintenanceActivities.setTolerenceLowerlimit(assetRequestModel.getTolerenceLowerlimit());
            return assetMaintenanceActivities;

        }
    }

    public AssetChecklist saveOrUpdateAssetChecklist(AssetRequestModel assetRequestModel, Assets assets) {
        if (assets.getAssetChecklist() == null) {
            AssetChecklist assetChecklist = new AssetChecklist();
            assetChecklist.setAssets(assets);
            assetChecklist.setCheckList(assetRequestModel.getCheckList());
            assetChecklist.setRemarks(assetRequestModel.getRemarks());
            return assetChecklist;
        } else {
            AssetChecklist assetChecklist = assets.getAssetChecklist();
            assetChecklist.setAssets(assets);
            assetChecklist.setCheckList(assetRequestModel.getCheckList());
            assetChecklist.setRemarks(assetRequestModel.getRemarks());
            return assetChecklist;

        }
    }

    public void deleteAssets(Long id) {
        assetRepository.deleteById(id);
    }


}
