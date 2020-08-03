package com.erp.mastro.model.request;

import com.erp.mastro.entities.Branch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private String tanNo;
    private String vatTinNo;
    private String juridical ;
    private String sTaxNo;
    private String cstTinNo;
    private String panNo;
    private String pfAccount;
    private String esicAccount ;
    private String eccNo ;
    private String plaNo ;
    private String range;
    private String division;
    private String rangeAddress;
    private String divisionAddress;
    private String Commissionerate;
    private String exempted;
    private String commissionerateAddress;
    private String limit;
    private String gstIn;
    private String cinNo;
    private Date creationDate;
    private Date vatDate;
    private Date cstdate;
    private Date staxDate;
    private Date panDate;
    private Date pfDate;
    private Date esicDate;
    private Date cinDate;
    private String sCreationDate;
    private String sVatDate;
    private String sCstdate;
    private String sStaxDate;
    private String sPanDate;
    private String sPfDate;
    private String sEsicDate;
    private String sCinDate;

    //    private String dateFormatter ="MM/dd/yyyy";
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");

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
        //this.creationDate = branch.getCreationDate();
        this.tanNo = branch.getBranchRegistration().getTanNo();
        this.vatTinNo = branch.getBranchRegistration().getVatTinNo();
        if(branch.getBranchRegistration().getVatDate()!=null){
            this.vatDate = branch.getBranchRegistration().getVatDate();
        }

        this.cstTinNo = branch.getBranchRegistration().getCstTinNo();
        if(branch.getBranchRegistration().getCstdate()!=null){
            this.cstdate = branch.getBranchRegistration().getCstdate();

        }
        this.juridical = branch.getBranchRegistration().getJuridical();
        this.sTaxNo = branch.getBranchRegistration().getSTaxNo();
        if(branch.getBranchRegistration().getStaxDate()!=null){
            this.staxDate = branch.getBranchRegistration().getStaxDate();
        }

        this.panNo = branch.getBranchRegistration().getPanNo();
        if(branch.getBranchRegistration().getPanDate()!=null){
            this.panDate = branch.getBranchRegistration().getPanDate();
        }

        this.pfAccount = branch.getBranchRegistration().getPfAccount();
        if(branch.getBranchRegistration().getPfDate()!=null){
            this.pfDate = branch.getBranchRegistration().getPfDate();
        }

        this.esicAccount = branch.getBranchRegistration().getEsicAccount();
        if(branch.getBranchRegistration().getEsicDate()!=null){
            this.esicDate = branch.getBranchRegistration().getEsicDate();
        }

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
        if (branch.getBranchRegistration().getCinDate() != null) {
            this.cinDate = branch.getBranchRegistration().getCinDate();
        }

    }

    public String getsCreationDate() {
        if (sCreationDate != null) {
            return sCreationDate;
        }
        Date date = getCreationDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsCreationDate(String sCreationDate) {
        this.sCreationDate = sCreationDate;
    }

    public String getsVatDate() {
        if (sVatDate != null) {
            return sVatDate;
        }
        Date date = getVatDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsVatDate(String sVatDate) {
        this.sVatDate = sVatDate;
    }

    public String getsCstdate() {
        if (sCstdate != null) {
            return sCstdate;
        }
        Date date = getCstdate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsCstdate(String sCstdate) {
        this.sCstdate = sCstdate;
    }

    public String getsStaxDate() {
        if (sStaxDate != null) {
            return sStaxDate;
        }
        Date date = getStaxDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsStaxDate(String sStaxDate) {
        this.sStaxDate = sStaxDate;
    }

    public String getsPanDate() {
        if (sPanDate != null) {
            return sPanDate;
        }
        Date date = getPanDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsPanDate(String sPanDate) {
        this.sPanDate = sPanDate;
    }

    public String getsPfDate() {
        if (sPfDate != null) {
            return sPfDate;
        }
        Date date = getPfDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsPfDate(String sPfDate) {
        this.sPfDate = sPfDate;
    }

    public String getsEsicDate() {
        if (sEsicDate != null) {
            return sEsicDate;
        }
        Date date = getEsicDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public void setsEsicDate(String sEsicDate) {
        this.sEsicDate = sEsicDate;
    }

    public String getsCinDate() {
        if (sCinDate != null) {
            return sCinDate;
        }
        Date date = getCinDate();
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public BranchRequestModel() {
    }

    public void setsCinDate(String sCinDate) {
        this.sCinDate = sCinDate;
    }

    @Override
    public String toString() {
        return "BranchRequestModel{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", stateName='" + stateName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", AreaName='" + AreaName + '\'' +
                ", branchPrefix='" + branchPrefix + '\'' +
                ", localCurrency='" + localCurrency + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", faxNo='" + faxNo + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", creationDate=" + creationDate +
                ", tanNo='" + tanNo + '\'' +
                ", vatTinNo='" + vatTinNo + '\'' +
                ", vatDate=" + vatDate +
                ", cstTinNo='" + cstTinNo + '\'' +
                ", cstdate=" + cstdate +
                ", juridical='" + juridical + '\'' +
                ", sTaxNo='" + sTaxNo + '\'' +
                ", staxDate=" + staxDate +
                ", panNo='" + panNo + '\'' +
                ", panDate='" + panDate + '\'' +
                ", pfAccount='" + pfAccount + '\'' +
                ", pfDate='" + pfDate + '\'' +
                ", esicAccount='" + esicAccount + '\'' +
                ", esicDate='" + esicDate + '\'' +
                ", eccNo='" + eccNo + '\'' +
                ", plaNo='" + plaNo + '\'' +
                ", range='" + range + '\'' +
                ", division='" + division + '\'' +
                ", rangeAddress='" + rangeAddress + '\'' +
                ", divisionAddress='" + divisionAddress + '\'' +
                ", Commissionerate='" + Commissionerate + '\'' +
                ", exempted='" + exempted + '\'' +
                ", commissionerateAddress='" + commissionerateAddress + '\'' +
                ", limit='" + limit + '\'' +
                ", gstIn='" + gstIn + '\'' +
                ", cinNo='" + cinNo + '\'' +
                ", cinDate=" + cinDate +
                '}';
    }
}
