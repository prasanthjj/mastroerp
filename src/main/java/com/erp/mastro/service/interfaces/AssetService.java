package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;

import java.util.List;
import java.util.Set;

public interface AssetService {

    List<Assets> getAllAssets();

    Assets getAssetsById(Long id);

    void saveOrUpdateAssets(Assets assets);

    void saveOrUpdateAssetCharacteristics(Assets assets, AssetCharacteristics assetCharacteristics);

    void saveOrUpdateAssetMaintenanceActivities(Assets assets, AssetMaintenanceActivities assetMaintenanceActivities);

    void saveOrUpdateAssetChecklist(Assets assets, AssetChecklist assetChecklist);

    void deleteAssets(Long id);

}

