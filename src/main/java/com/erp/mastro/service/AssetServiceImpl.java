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
import java.util.HashSet;
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
            Set<AssetCharacteristics> assetCharacteristics = saveOrUpdateAssetCharacteristics(assetRequestModel, assets);
            assets.setAssetCharacteristics(assetCharacteristics);
            Set<AssetMaintenanceActivities> assetMaintenanceActivities = saveOrUpdateAssetMaintenanceActivities(assetRequestModel, assets);
            assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
            Set<AssetChecklist> assetChecklist = saveOrUpdateAssetChecklist(assetRequestModel, assets);
            assets.setAssetChecklists(assetChecklist);
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
            Set<AssetCharacteristics> assetCharacteristics = saveOrUpdateAssetCharacteristics(assetRequestModel, assets);
            assets.setAssetCharacteristics(assetCharacteristics);
            Set<AssetMaintenanceActivities> assetMaintenanceActivities = saveOrUpdateAssetMaintenanceActivities(assetRequestModel, assets);
            assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
            Set<AssetChecklist> assetChecklist = saveOrUpdateAssetChecklist(assetRequestModel, assets);
            assets.setAssetChecklists(assetChecklist);
            assetRepository.save(assets);
        }
    }

    public Set<AssetCharacteristics> saveOrUpdateAssetCharacteristics(AssetRequestModel assetRequestModel, Assets assets) {

        Set<AssetCharacteristics> assetCharacteristicsSet = new HashSet<>();
        assets.getAssetCharacteristics().clear();
        if (assetRequestModel.getAssetCharacteristicsModel().isEmpty() == false) {
            assetRequestModel.getAssetCharacteristicsModel().parallelStream()
                    .forEach(x -> {
                        AssetCharacteristics assetCharacteristics = new AssetCharacteristics();
                        assetCharacteristics.setAssetRemarks(x.getAssetRemarks());
                        assetCharacteristics.setValue(x.getValue());
                        assetCharacteristics.setCharacter(x.getCharacter());
                        assetCharacteristics.setAsset(assets);
                        assetCharacteristicsSet.add(assetCharacteristics);

                    });
        }
        return assetCharacteristicsSet;
    }

    public Set<AssetMaintenanceActivities> saveOrUpdateAssetMaintenanceActivities(AssetRequestModel assetRequestModel, Assets assets) {

        Set<AssetMaintenanceActivities> assetMaintenanceActivitySet = new HashSet<>();
        assets.getAssetMaintenanceActivities().clear();
        if (assetRequestModel.getAssetMaintenanceActivitiesModel().isEmpty() == false) {
            assetRequestModel.getAssetMaintenanceActivitiesModel().parallelStream()
                    .forEach(x -> {
                        AssetMaintenanceActivities assetMaintenanceActivities = new AssetMaintenanceActivities();
                        assetMaintenanceActivities.setActivityName(x.getActivityName());
                        assetMaintenanceActivities.setCategory(x.getCategory());
                        assetMaintenanceActivities.setFrequency(x.getFrequency());
                        assetMaintenanceActivities.setStandardObservation(x.getStandardObservation());
                        assetMaintenanceActivities.setUpperLimit(x.getUpperLimit());
                        assetMaintenanceActivities.setTolerenceLowerlimit(x.getTolerenceLowerlimit());
                        assetMaintenanceActivities.setAsset(assets);
                        assetMaintenanceActivitySet.add(assetMaintenanceActivities);
                    });

        }
        return assetMaintenanceActivitySet;
    }

    public Set<AssetChecklist> saveOrUpdateAssetChecklist(AssetRequestModel assetRequestModel, Assets assets) {

        Set<AssetChecklist> assetChecklistSet = new HashSet<>();
        assets.getAssetChecklists().clear();
        if (assetRequestModel.getAssetCheckListModel().isEmpty() == false) {
            assetRequestModel.getAssetCheckListModel().parallelStream()
                    .forEach(x -> {
                        AssetChecklist assetChecklist = new AssetChecklist();
                        assetChecklist.setCheckList(x.getCheckList());
                        assetChecklist.setRemarks(x.getRemarks());
                        assetChecklist.setAsset(assets);
                        assetChecklistSet.add(assetChecklist);

                    });

        }
        return assetChecklistSet;
    }

    public void deleteAssets(Long id) {
        assetRepository.deleteById(id);
    }


}
