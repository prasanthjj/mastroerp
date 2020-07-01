package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.model.request.AssetRequestModel;

import java.util.List;

public interface AssetService {

    List<Assets> getAllAssets();

    Assets getAssetsById(Long id);

    void saveOrUpdateAssets(AssetRequestModel assetRequestModel);

    void saveOrUpdateAssetCharacteristics(Assets assets, AssetCharacteristics assetCharacteristics);

    void saveOrUpdateAssetMaintenanceActivities(Assets assets, AssetMaintenanceActivities assetMaintenanceActivities);

    void saveOrUpdateAssetChecklist(Assets assets, AssetChecklist assetChecklist);

    void deleteAssets(Long id);

}

