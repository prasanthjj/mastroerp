package com.erp.mastro.service;

import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @MockBean
    private AssetRepository assetRepository;
    private boolean True;
    private boolean False;

    public List<Assets> addAsset() {
        List<Assets> asset = new ArrayList<Assets>();
        Stream<Assets> stream = Stream.of(new Assets(1L, "Machine1", "E1", "S1", "S1a", "123", "2H", new Date(), new Date(), "12L", "P1", True, True, "M1"),
                new Assets(2L, "Machine2", "E2", "S2", "S2b", "456", "5H", new Date(), new Date(), "5L", "P2", False, False, "M2"));
        asset = stream.collect(Collectors.toList());
        return asset;
    }

    public Assets addAssets() {
        Assets assets = new Assets(3L, "M3", "E3", "S3", "S31", "333", "3H", new Date(), new Date(), "3L", "P2", False, False, "M3");
        return assets;
    }

    @Test
    public void testGetAssetSizeEqual() {
        when(assetRepository.findAll()).thenReturn(addAsset());
        Assert.assertEquals(2, assetService.getAllAssets().size());
    }

    @Test
    public void testGetAssetSizeNotEqual() {
        when(assetRepository.findAll()).thenReturn(addAsset());
        Assert.assertNotEquals(1, assetService.getAllAssets().size());
    }

    @Test
    public void testGetById() {

        when(assetRepository.findById(3L)).thenReturn(Optional.of(addAssets()));
        Assert.assertEquals(addAssets().getId(), assetService.getAssetsById(addAssets().getId()).getId());
    }

    @Test
    public void testSaveAsset() {
        Assets assets = new Assets(3L, "M3", "E3", "S3", "S31", "333", "3H", new Date(), new Date(), "3L", "P2", False, False, "M3");
        assetService.saveOrUpdateAssets(assets);
        verify(assetRepository, times(1)).save(assets);
    }

    @Test
    public void testDeletAsset() {
        assetService.deleteAssets(addAssets().getId());
        verify(assetRepository, times(1)).deleteById(addAssets().getId());
    }

    @Test
    public void testGetAssetValidationSuccess() {
        when(assetRepository.findById(addAssets().getId())).thenReturn(Optional.of(addAssets()));
        Assert.assertEquals("M3", assetService.getAssetsById(addAssets().getId()).getAssetName());
        Assert.assertEquals("E3", assetService.getAssetsById(addAssets().getId()).getAssetType());
        Assert.assertEquals("S3", assetService.getAssetsById(addAssets().getId()).getAssetLocation());
        Assert.assertEquals("S31", assetService.getAssetsById(addAssets().getId()).getSubLocation());
        Assert.assertEquals("333", assetService.getAssetsById(addAssets().getId()).getPartyNo());
        Assert.assertEquals("3H", assetService.getAssetsById(addAssets().getId()).getHoursUtilized());
        Assert.assertEquals("3L", assetService.getAssetsById(addAssets().getId()).getCapacity());
        Assert.assertEquals("M3", assetService.getAssetsById(addAssets().getId()).getMake());
        Assert.assertEquals(False, assetService.getAssetsById(addAssets().getId()).isActive());
        Assert.assertEquals(False, assetService.getAssetsById(addAssets().getId()).isMaintenanceRequired());
    }

    @Test
    public void testSaveAssetCharacteristics() {
        Assets assets = addAssets();
        AssetCharacteristics assetCharacteristics = new AssetCharacteristics(1L, "A", "25", "abc");
        assetService.saveOrUpdateAssetCharacteristics(assets, assetCharacteristics);
        verify(assetRepository, times(1)).save(assets);
    }

    @Test
    public void testSaveAssetMaintenanceActivities() {
        Assets assets = addAssets();
        AssetMaintenanceActivities assetMaintenanceActivities = new AssetMaintenanceActivities(1L, "S1", "5", "2", "2", "120", "C1");
        assetService.saveOrUpdateAssetMaintenanceActivities(assets, assetMaintenanceActivities);
        verify(assetRepository, times(1)).save(assets);
    }

    @Test
    public void testSaveAssetCheckList() {
        Assets assets = addAssets();
        AssetChecklist assetChecklist = new AssetChecklist(1L, "Cl", "R1");
        assetService.saveOrUpdateAssetChecklist(assets, assetChecklist);
        verify(assetRepository, times(1)).save(assets);
    }

}
