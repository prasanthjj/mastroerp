package com.erp.mastro.model.request;

import com.erp.mastro.entities.Branch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class BranchRequestModel {

    private Long id;
    private String branchName;
    private String countryName;
    private String branchCode;
    private String stateName;
    private String cityName;
    private String AreaName;
    private String branchPrefix;
    private String localCurrency;
    private String emailId;
    private String phoneNo;
    private String address;
    private String website;
    private String branchAddress;
    private String faxNo;
    private String pinCode;
    private Date creationDate;
    private String tanNo;
    private String vatTinNo;
    private Date vatDate;
    private String cstTinNo;
    private Date cstdate ;
    private String juridical ;
    private String sTaxNo;
    private Date staxDate ;
    private String panNo;
    private String panDate ;
    private String pfAccount;
    private String pfDate ;
    private String esicAccount ;
    private String esicDate ;
    private String eccNo ;
    private String plaNo ;
    private String range ;
    private String division ;
    private String rangeAddress ;
    private String divisionAddress;
    private String Commissionerate ;
    private String exempted ;
    private String commissionerateAddress;
    private String limit;
    private String gstIn;
    private String cinNo;
    private Date cinDate;

    public BranchRequestModel() {}

    public BranchRequestModel(Branch branch) {
        this.id = branch.getId();
        this.branchName = branch.getBranchName();
        this.countryName = branch.getCountryName();
        this.branchCode = branch.getBranchCode();
        this.stateName = branch.getStateName();
        this.cityName = branch.getCityName();
        this.branchPrefix = branch.getBranchPrefix();
        this.localCurrency = branch.getLocalCurrency();
        this.emailId = branch.getEmailId();
        this.phoneNo = branch.getPhoneNo();
        this.address = branch.getAddress();
        this.website = branch.getWebsite();
        this.branchAddress = branch.getBranchAddress();
        this.faxNo = branch.getFaxNo();
        this.pinCode = branch.getPinCode();
        this.creationDate = branch.getCreationDate();
        this.tanNo = branch.getBranchRegistration().getTanNo();
        this.vatTinNo = branch.getBranchRegistration().getVatTinNo();
        this.vatDate = branch.getBranchRegistration().getVatDate();
        this.cstTinNo = branch.getBranchRegistration().getCstTinNo();
        this.cstdate = branch.getBranchRegistration().getCstdate();
        this.juridical = branch.getBranchRegistration().getJuridical();
        this.sTaxNo = branch.getBranchRegistration().getSTaxNo();
        this.staxDate = branch.getBranchRegistration().getStaxDate();
        this.panNo = branch.getBranchRegistration().getPanNo();
        this.panDate = branch.getBranchRegistration().getPanDate();
        this.pfAccount = branch.getBranchRegistration().getPfAccount();
        this.pfDate = branch.getBranchRegistration().getPfDate();
        this.esicAccount = branch.getBranchRegistration().getEsicAccount();
        this.esicDate = branch.getBranchRegistration().getEsicDate();
        this.eccNo = branch.getBranchRegistration().getEccNo();
        this.plaNo = branch.getBranchRegistration().getPlaNo();
        this.range = branch.getBranchRegistration().getRange();
        this.division = branch.getBranchRegistration().getDivision();
        this.rangeAddress = branch.getBranchRegistration().getRangeAddress();
        this.divisionAddress = branch.getBranchRegistration().getDivisionAddress();
        this.Commissionerate = branch.getBranchRegistration().getCommissionerate();
        this.exempted = branch.getBranchRegistration().getExempted();
        this.commissionerateAddress = branch.getBranchRegistration().getCommissionerateAddress();
        this.limit = branch.getBranchRegistration().getLimit();
        this.gstIn = branch.getBranchRegistration().getGstIn();
        this.cinNo = branch.getBranchRegistration().getCinNo();
        this.cinDate = branch.getBranchRegistration().getCinDate();
    }
}
