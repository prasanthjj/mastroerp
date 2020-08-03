package com.erp.mastro.service;

import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @MockBean
    private AssetRepository assetRepository;

    private boolean True;
    private boolean False;

    public AssetRequestModel createAssetModel() {
        AssetRequestModel assetRequestModel = new AssetRequestModel();
        assetRequestModel.setMaintenanceRequired(true);
        assetRequestModel.setAssetName("Table");
        assetRequestModel.setAssetLocation("kottayam");
        assetRequestModel.setSubLocation("vaikom");
        assetRequestModel.setPartyNo("11");
        assetRequestModel.setMaintenancePriority("high");
        assetRequestModel.setInstallationDate(new Date());
        assetRequestModel.setEffectiveDate(new Date());
        assetRequestModel.setAssetType("furniture");
        assetRequestModel.setAssetCharacteristicsModel(createAssetCharModel());
        assetRequestModel.setAssetMaintenanceActivitiesModel(createAssetMaintananceModel());
        assetRequestModel.setAssetCheckListModel(createAssetCheckListModel());
        return assetRequestModel;
    }

    public List<AssetRequestModel.AssetCharacteristicsModel> createAssetCharModel() {

        List<AssetRequestModel.AssetCharacteristicsModel> assetCharacteristicsModels = new ArrayList<>();
        AssetRequestModel.AssetCharacteristicsModel assetCharacteristicsModel1 = new AssetRequestModel.AssetCharacteristicsModel();
        assetCharacteristicsModel1.setCharacter("Colour");
        assetCharacteristicsModel1.setValue("Red");
        assetCharacteristicsModels.add(assetCharacteristicsModel1);
        AssetRequestModel.AssetCharacteristicsModel assetCharacteristicsModel2 = new AssetRequestModel.AssetCharacteristicsModel();
        assetCharacteristicsModel2.setCharacter("size");
        assetCharacteristicsModel2.setValue("50m");
        assetCharacteristicsModels.add(assetCharacteristicsModel2);
        return assetCharacteristicsModels;
    }

    public List<AssetRequestModel.AssetMaintenanceActivitiesModel> createAssetMaintananceModel() {

        List<AssetRequestModel.AssetMaintenanceActivitiesModel> assetMaintanceModels = new ArrayList<>();
        AssetRequestModel.AssetMaintenanceActivitiesModel assetMaintanceModel1 = new AssetRequestModel.AssetMaintenanceActivitiesModel();
        assetMaintanceModel1.setActivityName("Painting");
        assetMaintanceModel1.setStandardObservation("ppp");
        assetMaintanceModels.add(assetMaintanceModel1);
        AssetRequestModel.AssetMaintenanceActivitiesModel assetMaintanceModel2 = new AssetRequestModel.AssetMaintenanceActivitiesModel();
        assetMaintanceModel2.setActivityName("Washing");
        assetMaintanceModel2.setStandardObservation("wwppp");
        assetMaintanceModels.add(assetMaintanceModel2);
        return assetMaintanceModels;
    }

    public List<AssetRequestModel.AssetCheckListModel> createAssetCheckListModel() {

        List<AssetRequestModel.AssetCheckListModel> assetCheckModels = new ArrayList<>();
        AssetRequestModel.AssetCheckListModel assetCheckModel1 = new AssetRequestModel.AssetCheckListModel();
        assetCheckModel1.setCheckList("check1");
        assetCheckModel1.setRemarks("ok");
        assetCheckModels.add(assetCheckModel1);
        AssetRequestModel.AssetCheckListModel assetCheckModel2 = new AssetRequestModel.AssetCheckListModel();
        assetCheckModel2.setCheckList("check2");
        assetCheckModel2.setRemarks("ok");
        assetCheckModels.add(assetCheckModel2);
        return assetCheckModels;
    }

    public Assets getAsset() {
        Assets assets = new Assets(3L, "M3", "E3", "S3", "S31", "333", 25.5, new Date(), new Date(), "3L", "P2", False, False, "M3", getAssetCharacters());
        return assets;
    }

    public Set<AssetCharacteristics> getAssetCharacters() {
        Set<AssetCharacteristics> assetCharacteristicsSet = new HashSet<>();
        AssetCharacteristics assetCharacteristics = new AssetCharacteristics(1L, "colour", "red", "ccc");
        assetCharacteristicsSet.add(assetCharacteristics);
        return assetCharacteristicsSet;
    }

    @Test
    public void testSaveAsset() throws ModelNotFoundException {

        Assets assets = assetService.saveOrUpdateAssets(createAssetModel());
        Assert.assertEquals("Table", assets.getAssetName());
        Assert.assertEquals("kottayam", assets.getAssetLocation());
        Assert.assertEquals("vaikom", assets.getSubLocation());
        Assert.assertEquals("high", assets.getMaintenancePriority());
        Assert.assertEquals("furniture", assets.getAssetType());

    }

    @Test
    public void testAssetModelNull() {

        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                assetService.saveOrUpdateAssets(null))
                .isExactlyInstanceOf(ModelNotFoundException.class);

    }

    @Test
    public void testGetById() {

        when(assetRepository.findById(3L)).thenReturn(Optional.of(getAsset()));
        Assert.assertEquals(getAsset().getId(), assetService.getAssetsById(getAsset().getId()).getId());
    }

    @Test
    public void testDeletAsset() {
        assetService.deleteAssets(getAsset().getId());
        verify(assetRepository, times(1)).deleteById(getAsset().getId());
    }
}
