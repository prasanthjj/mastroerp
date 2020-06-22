package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Assets;

import java.util.List;

public interface AssetService {

    List<Assets> getAllAssets();

    Assets getAssetsById(Long id);

    void saveOrUpdateAssets(Assets assets);

    void deleteAssets(Long id);

}
