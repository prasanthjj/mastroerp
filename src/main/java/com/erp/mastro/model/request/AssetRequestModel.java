package com.erp.mastro.model.request;


import com.erp.mastro.entities.AssetCharacteristics;
import com.erp.mastro.entities.AssetChecklist;
import com.erp.mastro.entities.AssetMaintenanceActivities;
import com.erp.mastro.entities.Assets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getPartyNo() {
        return partyNo;
    }

    public void setPartyNo(String partyNo) {
        this.partyNo = partyNo;
    }

    public Double getHoursUtilized() {
        return hoursUtilized;
    }

    public void setHoursUtilized(Double hoursUtilized) {
        this.hoursUtilized = hoursUtilized;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getMaintenancePriority() {
        return maintenancePriority;
    }

    public void setMaintenancePriority(String maintenancePriority) {
        this.maintenancePriority = maintenancePriority;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getMaintenanceRequired() {
        return maintenanceRequired;
    }

    public void setMaintenanceRequired(Boolean maintenanceRequired) {
        this.maintenanceRequired = maintenanceRequired;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public List<AssetCharacteristicsModel> getAssetCharacteristicsModel() {
        return assetCharacteristicsModel;
    }

    public void setAssetCharacteristicsModel(List<AssetCharacteristicsModel> assetCharacteristicsModel) {
        this.assetCharacteristicsModel = assetCharacteristicsModel;
    }


    public List<AssetMaintenanceActivitiesModel> getAssetMaintenanceActivitiesModel() {
        return assetMaintenanceActivitiesModel;
    }

    public void setAssetMaintenanceActivitiesModel(List<AssetMaintenanceActivitiesModel> assetMaintenanceActivitiesModel) {
        this.assetMaintenanceActivitiesModel = assetMaintenanceActivitiesModel;
    }

    public List<AssetCheckListModel> getAssetCheckListModel() {
        return assetCheckListModel;
    }

    public void setAssetCheckListModel(List<AssetCheckListModel> assetCheckListModel) {
        this.assetCheckListModel = assetCheckListModel;
    }

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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getAssetRemarks() {
            return assetRemarks;
        }

        public void setAssetRemarks(String assetRemarks) {
            this.assetRemarks = assetRemarks;
        }
    }

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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getUpperLimit() {
            return upperLimit;
        }

        public void setUpperLimit(String upperLimit) {
            this.upperLimit = upperLimit;
        }

        public String getStandardObservation() {
            return standardObservation;
        }

        public void setStandardObservation(String standardObservation) {
            this.standardObservation = standardObservation;
        }

        public String getTolerenceLowerlimit() {
            return tolerenceLowerlimit;
        }

        public void setTolerenceLowerlimit(String tolerenceLowerlimit) {
            this.tolerenceLowerlimit = tolerenceLowerlimit;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Double getTolerence() {
            return tolerence;
        }

        public void setTolerence(Double tolerence) {
            this.tolerence = tolerence;
        }
    }

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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCheckList() {
            return checkList;
        }

        public void setCheckList(String checkList) {
            this.checkList = checkList;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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
