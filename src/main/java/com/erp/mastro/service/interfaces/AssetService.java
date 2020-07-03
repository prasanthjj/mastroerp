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

    AssetCharacteristics saveOrUpdateAssetCharacteristics(AssetRequestModel assetRequestModel, Assets assets);

    AssetMaintenanceActivities saveOrUpdateAssetMaintenanceActivities(AssetRequestModel assetRequestModel, Assets assets);

    AssetChecklist saveOrUpdateAssetChecklist(AssetRequestModel assetRequestModel, Assets assets);

    void deleteAssets(Long id);

}

