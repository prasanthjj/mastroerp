package com.erp.mastro.model.request;


import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class AssetRequestModel {
    private Long id;
    private String assetNo;
    private String assetName;
    private String assetType;
    private String assetLocation;
    private String subLocation;
    private String partyNo;
    private Double hoursUtilized;
    private Date installationDate;
    private Date effectiveDate;
    private String capacity;
    private String maintenancePriority;
    private Boolean active;
    private Boolean maintenanceRequired;
    private String make;
    private List<AssetCharacteristicsModel> assetCharacteristicsModel = new ArrayList<>();
    private List<AssetMaintenanceActivitiesModel> assetMaintenanceActivitiesModel = new ArrayList<>();
    private List<AssetCheckListModel> assetCheckListModel = new ArrayList<>();

    public AssetRequestModel() {

    }

    public AssetRequestModel(Assets assets) {
        if (assets != null) {
            this.id = assets.getId();
            this.setAssetName(assets.getAssetName());
            this.setAssetLocation(assets.getAssetLocation());
            this.setActive(assets.getActive());
            this.setAssetType(assets.getAssetType());
            this.setCapacity(assets.getCapacity());
            this.setEffectiveDate(assets.getEffectiveDate());
            this.setHoursUtilized(assets.getHoursUtilized());
            this.setInstallationDate(assets.getInstallationDate());
            this.setMaintenancePriority(assets.getMaintenancePriority());
            this.setMake(assets.getMake());
            this.setPartyNo(assets.getPartyNo());
            this.setSubLocation(assets.getSubLocation());
            this.setMaintenanceRequired(assets.getMaintenanceRequired());
            assets.getAssetCharacteristics().parallelStream().forEach(x -> this.assetCharacteristicsModel.add(new AssetCharacteristicsModel(x)));
            assets.getAssetMaintenanceActivities().parallelStream().forEach(x -> this.assetMaintenanceActivitiesModel.add(new AssetMaintenanceActivitiesModel(x)));
            assets.getAssetChecklists().parallelStream().forEach(x -> this.assetCheckListModel.add(new AssetCheckListModel(x)));
        }
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class AssetCharacteristicsModel {

        private Long id;
        private String character;
        private String value;
        private String assetRemarks;

        public AssetCharacteristicsModel() {

        }

        public AssetCharacteristicsModel(AssetCharacteristics assetCharacteristics) {
            this.id = assetCharacteristics.getId();
            this.character = assetCharacteristics.getCharacter();
            this.value = assetCharacteristics.getValue();
            this.assetRemarks = assetCharacteristics.getAssetRemarks();
        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class AssetMaintenanceActivitiesModel {

        private Long id;
        private String activityName;
        private String upperLimit;
        private String standardObservation;
        private String tolerenceLowerlimit;
        private String frequency;
        private String category;
        private Double tolerence;

        public AssetMaintenanceActivitiesModel() {

        }

        public AssetMaintenanceActivitiesModel(AssetMaintenanceActivities assetMaintenanceActivities) {
            this.id = assetMaintenanceActivities.getId();
            this.activityName = assetMaintenanceActivities.getActivityName();
            this.category = assetMaintenanceActivities.getCategory();
            this.frequency = assetMaintenanceActivities.getFrequency();
            this.standardObservation = assetMaintenanceActivities.getStandardObservation();
            this.tolerence = assetMaintenanceActivities.getTolerence();
            this.tolerenceLowerlimit = assetMaintenanceActivities.getTolerenceLowerlimit();
            this.upperLimit = assetMaintenanceActivities.getUpperLimit();

        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class AssetCheckListModel {

        private Long id;
        private String checkList;
        private String remarks;

        public AssetCheckListModel() {

        }

        public AssetCheckListModel(AssetChecklist assetChecklist) {
            this.id = assetChecklist.getId();
            this.checkList = assetChecklist.getCheckList();
            this.remarks = assetChecklist.getRemarks();
        }
    }

    @Override
    public String toString() {
        return "AssetRequestModel{" +
                "id=" + id +
                ", assetNo='" + assetNo + '\'' +
                ", assetName='" + assetName + '\'' +
                ", assetType='" + assetType + '\'' +
                ", assetLocation='" + assetLocation + '\'' +
                ", subLocation='" + subLocation + '\'' +
                ", partyNo='" + partyNo + '\'' +
                ", hoursUtilized=" + hoursUtilized +
                ", installationDate=" + installationDate +
                ", effectiveDate=" + effectiveDate +
                ", capacity='" + capacity + '\'' +
                ", maintenancePriority='" + maintenancePriority + '\'' +
                ", active=" + active +
                ", maintenanceRequired=" + maintenanceRequired +
                ", make='" + make + '\'' +
                ", assetCharacteristicsModel=" + assetCharacteristicsModel +
                ", assetMaintenanceActivitiesModel=" + assetMaintenanceActivitiesModel +
                ", assetCheckListModel=" + assetCheckListModel +
                '}';
    }

}
