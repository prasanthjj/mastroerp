package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
/**
 * Service class for asset
 */
public class AssetServiceImpl implements AssetService {

    private Logger logger = LoggerFactory.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetRepository assetRepository;

    public List<Assets> getAllAssets() {
        List<Assets> assetsList = new ArrayList<Assets>();
        assetRepository.findAll().forEach(assets -> assetsList.add(assets));
        return assetsList;
    }

    /**
     * method to get asset accroding to id
     *
     * @param id
     * @return asset
     */
    public Assets getAssetsById(Long id) {

        Assets assets = new Assets();
        if (id != null) {
            MastroLogUtils.info(AssetService.class, "Going to getAssetsBy Id : {}" + id);
            assets = assetRepository.findById(id).get();
        }
        return assets;
    }

    /**
     * Save and edit aseet
     *
     * @param assetRequestModel
     * @return the asset
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Assets saveOrUpdateAssets(AssetRequestModel assetRequestModel) throws ModelNotFoundException {

        MastroLogUtils.info(AssetService.class, "Going to save asset  {}" + assetRequestModel.toString());
        Assets assets = new Assets();

        if (assetRequestModel == null) {
            throw new ModelNotFoundException("AssetRequestModel model is empty");
        } else {
            if (assetRequestModel.getId() == null) {

                MastroLogUtils.info(AssetService.class, "Going to Add asset  {}" + assetRequestModel);

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

                MastroLogUtils.info(AssetService.class, "Added " + assets.getAssetName() +" succesfully.");

            } else {
                MastroLogUtils.info(AssetService.class, "Going to edit asset  {}" + assetRequestModel.toString());

                assets = assetRepository.findById(assetRequestModel.getId()).get();
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

                MastroLogUtils.info(AssetService.class, "Updated " + assets.getAssetName() +" succesfully.");

            }
            return assets;
        }

    }

    /**
     * Save and edit asset characteristics
     *
     * @param assetRequestModel
     * @param assets
     * @return set of asset characteristics
     */
    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetCharacteristics> saveOrUpdateAssetCharacteristics(AssetRequestModel assetRequestModel, Assets assets) {

        MastroLogUtils.info(AssetService.class, "Going to save asset characteristics  {}" + assetRequestModel.toString());
        Set<AssetCharacteristics> assetCharacteristicsSet = new HashSet<>();

        if (assetRequestModel.getAssetCharacteristicsModel().isEmpty() == false) {

            assetRequestModel.getAssetCharacteristicsModel().parallelStream()
                    .forEach(x -> {
                        AssetCharacteristics assetCharacteristics;
                        if (!containsInList(x.getId(), assets.getAssetCharacteristics().stream().filter(assetdata -> (null != assetdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            assetCharacteristics = new AssetCharacteristics();
                            assetCharacteristics.setAssetRemarks(x.getAssetRemarks());
                            assetCharacteristics.setValue(x.getValue());
                            assetCharacteristics.setCharacter(x.getCharacter());
                            assetCharacteristicsSet.add(assetCharacteristics);
                        } else {

                            assetCharacteristics = assets.getAssetCharacteristics().stream().filter(assetdata -> (null != assetdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
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

    /**
     * save and edit assetmaintance activities
     *
     * @param assetRequestModel
     * @param assets
     * @return set of maintance activities
     */
    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetMaintenanceActivities> saveOrUpdateAssetMaintenanceActivities(AssetRequestModel assetRequestModel, Assets assets) {
        MastroLogUtils.info(AssetService.class, "Going to save asset MaintenanceActivities  {}" + assetRequestModel.toString());
        Set<AssetMaintenanceActivities> assetMaintenanceActivitySet = new HashSet<>();
        if (assetRequestModel.getAssetMaintenanceActivitiesModel().isEmpty() == false) {
            assetRequestModel.getAssetMaintenanceActivitiesModel().parallelStream()
                    .forEach(x -> {
                        AssetMaintenanceActivities assetMaintenanceActivities;
                        if (!containsInList(x.getId(), assets.getAssetMaintenanceActivities().stream().filter(assetMaindata -> (null != assetMaindata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            assetMaintenanceActivities = new AssetMaintenanceActivities();
                            assetMaintenanceActivities.setActivityName(x.getActivityName());
                            assetMaintenanceActivities.setCategory(x.getCategory());
                            assetMaintenanceActivities.setFrequency(x.getFrequency());
                            assetMaintenanceActivities.setStandardObservation(x.getStandardObservation());
                            assetMaintenanceActivities.setUpperLimit(x.getUpperLimit());
                            assetMaintenanceActivities.setTolerenceLowerlimit(x.getTolerenceLowerlimit());
                            assetMaintenanceActivities.setTolerence(x.getTolerence());
                            assetMaintenanceActivitySet.add(assetMaintenanceActivities);
                        } else {

                            assetMaintenanceActivities = assets.getAssetMaintenanceActivities().stream().filter(assetMaindata -> (null != assetMaindata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
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

    /**
     * save and edit asset checklist
     *
     * @param assetRequestModel
     * @param assets
     * @return set of assetchecklist
     */
    @Transactional(rollbackOn = {Exception.class})
    public Set<AssetChecklist> saveOrUpdateAssetChecklist(AssetRequestModel assetRequestModel, Assets assets) {

        MastroLogUtils.info(AssetService.class, "Going to save asset Checklist  {}" + assetRequestModel.toString());
        Set<AssetChecklist> assetChecklistSet = new HashSet<>();
        if (assetRequestModel.getAssetCheckListModel().isEmpty() == false) {

            assetRequestModel.getAssetCheckListModel().parallelStream()
                    .forEach(x -> {
                        AssetChecklist assetChecklist;
                        if (!containsInList(x.getId(), assets.getAssetChecklists().stream().filter(assetCheckdata -> (null != assetCheckdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            assetChecklist = new AssetChecklist();
                            assetChecklist.setCheckList(x.getCheckList());
                            assetChecklist.setRemarks(x.getRemarks());
                            assetChecklistSet.add(assetChecklist);
                        } else {

                            assetChecklist = assets.getAssetChecklists().stream().filter(assetCheckdata -> (null != assetCheckdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
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

    /**
     * Delete asset
     *
     * @param id
     * @return asset
     */
    @Transactional(rollbackOn = {Exception.class})
    public Assets deleteAssetDetails(Long id) {
        Assets assets = new Assets();
        if (id != null) {
            assets = getAssetsById(id);
            assets.setAssetDeleteStatus(1);
            assetRepository.save(assets);

        }
        return assets;

    }

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    /**
     * Remonve blank maintance activities
     *
     * @param assetMaintenanceActivities
     */
    private void removeBlankMaintanceActivities(Set<AssetMaintenanceActivities> assetMaintenanceActivities) {
        assetMaintenanceActivities.removeIf(x -> x.getActivityName() == null);
    }

    /**
     * Remove blank checklists
     *
     * @param assetChecklists
     */
    private void removeBlankCheckLists(Set<AssetChecklist> assetChecklists) {
        assetChecklists.removeIf(x -> x.getCheckList() == null);
    }

    /**
     * Remove blank characteristics
     *
     * @param assetCharacteristics
     */
    private void removeBlankCharacteristics(Set<AssetCharacteristics> assetCharacteristics) {
        assetCharacteristics.removeIf(x -> x.getCharacter() == null);
    }

}
