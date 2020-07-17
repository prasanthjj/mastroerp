package com.erp.mastro.service;


import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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
        MastroLogUtils.info(AssetService.class, "Going to getAssetsBy Id : {}" + id);
        return assetRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateAssets(AssetRequestModel assetRequestModel) {

        MastroLogUtils.info(AssetService.class, "Going to save asset  {}" + assetRequestModel);
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
            if (assetRequestModel.getActive() != null) {
                assets.setActive(assetRequestModel.getActive());
            } else {
                assets.setActive(false);
            }
            if (assetRequestModel.getMaintenanceRequired() != null) {
                assets.setMaintenanceRequired(assetRequestModel.getMaintenanceRequired());
            } else {
                assets.setMaintenanceRequired(false);
            }
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
            if (assetRequestModel.getActive() != null) {
                assets.setActive(assetRequestModel.getActive());
            } else {
                assets.setActive(false);
            }
            if (assetRequestModel.getMaintenanceRequired() != null) {
                assets.setMaintenanceRequired(assetRequestModel.getMaintenanceRequired());
            } else {
                assets.setMaintenanceRequired(false);
            }
            assets.setMake(assetRequestModel.getMake());
            Set<AssetCharacteristics> assetCharacteristics = saveOrUpdateAssetCharacteristics(assetRequestModel, assets);
            assets.setAssetCharacteristics(assetCharacteristics);
            Set<AssetMaintenanceActivities> assetMaintenanceActivities = saveOrUpdateAssetMaintenanceActivities(assetRequestModel, assets);
            assets.setAssetMaintenanceActivities(assetMaintenanceActivities);
            Set<AssetChecklist> assetChecklist = saveOrUpdateAssetChecklist(assetRequestModel, assets);
            assets.setAssetChecklists(assetChecklist);
            assetRepository.save(assets);
        }
        MastroLogUtils.info(AssetService.class, "Going to save asset end {}" + assetRequestModel);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetCharacteristics> saveOrUpdateAssetCharacteristics(AssetRequestModel assetRequestModel, Assets assets) {

        MastroLogUtils.info(AssetService.class, "Going to save asset characteristics  {}" + assetRequestModel);
        Set<AssetCharacteristics> assetCharacteristicsSet = new HashSet<>();
        if (assetRequestModel.getAssetCharacteristicsModel().isEmpty() == false) {
            assetRequestModel.getAssetCharacteristicsModel().parallelStream()
                    .forEach(x -> {

                        if (!containsInList(x.getId(), assets.getAssetCharacteristics().stream().filter(assetdata -> (null != assetdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            AssetCharacteristics assetCharacteristics = new AssetCharacteristics();
                            assetCharacteristics.setAssetRemarks(x.getAssetRemarks());
                            assetCharacteristics.setValue(x.getValue());
                            assetCharacteristics.setCharacter(x.getCharacter());
                            assetCharacteristicsSet.add(assetCharacteristics);
                        } else {

                            AssetCharacteristics assetCharacteristics = assets.getAssetCharacteristics().stream().filter(assetdata -> (null != assetdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            assetCharacteristics.setAssetRemarks(x.getAssetRemarks());
                            assetCharacteristics.setValue(x.getValue());
                            assetCharacteristics.setCharacter(x.getCharacter());
                            assetCharacteristicsSet.add(assetCharacteristics);
                        }
                    });
        }
        removeBlankCharacteristics(assetCharacteristicsSet);
        return assetCharacteristicsSet;

    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetMaintenanceActivities> saveOrUpdateAssetMaintenanceActivities(AssetRequestModel assetRequestModel, Assets assets) {
        MastroLogUtils.info(AssetService.class, "Going to save asset MaintenanceActivities  {}" + assetRequestModel);
        Set<AssetMaintenanceActivities> assetMaintenanceActivitySet = new HashSet<>();
        if (assetRequestModel.getAssetMaintenanceActivitiesModel().isEmpty() == false) {
            assetRequestModel.getAssetMaintenanceActivitiesModel().parallelStream()
                    .forEach(x -> {

                        if (!containsInList(x.getId(), assets.getAssetMaintenanceActivities().stream().filter(assetMaindata -> (null != assetMaindata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            AssetMaintenanceActivities assetMaintenanceActivities = new AssetMaintenanceActivities();
                            assetMaintenanceActivities.setActivityName(x.getActivityName());
                            assetMaintenanceActivities.setCategory(x.getCategory());
                            assetMaintenanceActivities.setFrequency(x.getFrequency());
                            assetMaintenanceActivities.setStandardObservation(x.getStandardObservation());
                            assetMaintenanceActivities.setUpperLimit(x.getUpperLimit());
                            assetMaintenanceActivities.setTolerenceLowerlimit(x.getTolerenceLowerlimit());
                            assetMaintenanceActivities.setTolerence(x.getTolerence());
                            assetMaintenanceActivitySet.add(assetMaintenanceActivities);
                        } else {

                            AssetMaintenanceActivities assetMaintenanceActivities = assets.getAssetMaintenanceActivities().stream().filter(assetMaindata -> (null != assetMaindata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            assetMaintenanceActivities.setActivityName(x.getActivityName());
                            assetMaintenanceActivities.setCategory(x.getCategory());
                            assetMaintenanceActivities.setFrequency(x.getFrequency());
                            assetMaintenanceActivities.setStandardObservation(x.getStandardObservation());
                            assetMaintenanceActivities.setUpperLimit(x.getUpperLimit());
                            assetMaintenanceActivities.setTolerenceLowerlimit(x.getTolerenceLowerlimit());
                            assetMaintenanceActivities.setTolerence(x.getTolerence());
                            assetMaintenanceActivitySet.add(assetMaintenanceActivities);
                        }
                    });

        }
        removeBlankMaintanceActivities(assetMaintenanceActivitySet);
        return assetMaintenanceActivitySet;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetChecklist> saveOrUpdateAssetChecklist(AssetRequestModel assetRequestModel, Assets assets) {

        MastroLogUtils.info(AssetService.class, "Going to save asset Checklist  {}" + assetRequestModel);
        Set<AssetChecklist> assetChecklistSet = new HashSet<>();
        if (assetRequestModel.getAssetCheckListModel().isEmpty() == false) {
            assetRequestModel.getAssetCheckListModel().parallelStream()
                    .forEach(x -> {

                        if (!containsInList(x.getId(), assets.getAssetChecklists().stream().filter(assetCheckdata -> (null != assetCheckdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            AssetChecklist assetChecklist = new AssetChecklist();
                            assetChecklist.setCheckList(x.getCheckList());
                            assetChecklist.setRemarks(x.getRemarks());
                            assetChecklistSet.add(assetChecklist);
                        } else {

                            AssetChecklist assetChecklist = assets.getAssetChecklists().stream().filter(assetCheckdata -> (null != assetCheckdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            assetChecklist.setCheckList(x.getCheckList());
                            assetChecklist.setRemarks(x.getRemarks());
                            assetChecklistSet.add(assetChecklist);
                        }
                    });

        }
        removeBlankCheckLists(assetChecklistSet);
        return assetChecklistSet;
    }

    public void deleteAssets(Long id) {
        assetRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteAssetDetails(Long id) {

        Assets assets = getAssetsById(id);
        assets.setAssetDeleteStatus(1);
        assetRepository.save(assets);

    }

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    private void removeBlankMaintanceActivities(Set<AssetMaintenanceActivities> assetMaintenanceActivities) {
        assetMaintenanceActivities.removeIf(x -> x.getActivityName() == null);
    }

    private void removeBlankCheckLists(Set<AssetChecklist> assetChecklists) {
        assetChecklists.removeIf(x -> x.getCheckList() == null);
    }

    private void removeBlankCharacteristics(Set<AssetCharacteristics> assetCharacteristics) {
        assetCharacteristics.removeIf(x -> x.getCharacter() == null);
    }
}
