package com.erp.mastro.model.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartyRequestModel {

    private Long id;
    private String partyType;
    private String partyCode;
    private String partyName;
    private String aliasName;
    private String status;
    private String type;
    private String website;
    private String paymentTerms;
    private String referBy;
    private String source;
    private String categoryType;
    private String transporterName;
    private Date partyDate;
    private String oldReferCode;
    private String relationshipMananger;
    private String industryType;
    private String emailId;
    private String transactionType;
    private List<ContactDetailsModel> contactDetailsModelList = new ArrayList<>();
    private List<BillingDetailsModel> billingDetailsModelList = new ArrayList<>();
    private List<BankDetailsModel> bankDetailsModelList = new ArrayList<>();
    private List<CreditDetailsModel> creditDetailsModelList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getReferBy() {
        return referBy;
    }

    public void setReferBy(String referBy) {
        this.referBy = referBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public Date getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(Date partyDate) {
        this.partyDate = partyDate;
    }

    public String getOldReferCode() {
        return oldReferCode;
    }

    public void setOldReferCode(String oldReferCode) {
        this.oldReferCode = oldReferCode;
    }

    public String getRelationshipMananger() {
        return relationshipMananger;
    }

    public void setRelationshipMananger(String relationshipMananger) {
        this.relationshipMananger = relationshipMananger;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<ContactDetailsModel> getContactDetailsModelList() {
        return contactDetailsModelList;
    }

    public void setContactDetailsModelList(List<ContactDetailsModel> contactDetailsModelList) {
        this.contactDetailsModelList = contactDetailsModelList;
    }

    public List<BillingDetailsModel> getBillingDetailsModelList() {
        return billingDetailsModelList;
    }

    public void setBillingDetailsModelList(List<BillingDetailsModel> billingDetailsModelList) {
        this.billingDetailsModelList = billingDetailsModelList;
    }

    public List<BankDetailsModel> getBankDetailsModelList() {
        return bankDetailsModelList;
    }

    public void setBankDetailsModelList(List<BankDetailsModel> bankDetailsModelList) {
        this.bankDetailsModelList = bankDetailsModelList;
    }

    public List<CreditDetailsModel> getCreditDetailsModelList() {
        return creditDetailsModelList;
    }

    public void setCreditDetailsModelList(List<CreditDetailsModel> creditDetailsModelList) {
        this.creditDetailsModelList = creditDetailsModelList;
    }

    public static class ContactDetailsModel {

        private Long id;
        private String contactPersonName;
        private String contactType;
        private String designation;
        private String department;
        private String telephoneNo;
        private String altTelephoneNo;
        private String address;
        private String mobileNo;
        private String altMobileNo;
        private String faxNo;
        private String emailId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
        }

        public String getContactType() {
            return contactType;
        }

        public void setContactType(String contactType) {
            this.contactType = contactType;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getTelephoneNo() {
            return telephoneNo;
        }

        public void setTelephoneNo(String telephoneNo) {
            this.telephoneNo = telephoneNo;
        }

        public String getAltTelephoneNo() {
            return altTelephoneNo;
        }

        public void setAltTelephoneNo(String altTelephoneNo) {
            this.altTelephoneNo = altTelephoneNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getAltMobileNo() {
            return altMobileNo;
        }

        public void setAltMobileNo(String altMobileNo) {
            this.altMobileNo = altMobileNo;
        }

        public String getFaxNo() {
            return faxNo;
        }

        public void setFaxNo(String faxNo) {
            this.faxNo = faxNo;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

    }

    public static class BillingDetailsModel {

        private Long id;
        private String type;
        private String country;
        private String state;
        private String street;
        private String city;
        private String area;
        private String pinCode;
        private String designation;
        private String faxNo;
        private String telephoneNo;
        private String contactPersonName;
        private String gst;
        private String emailId;
        private String eccNo;
        private String commisionRate;
        private String rangeNo;
        private String division;
        private String rangeAddress;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPinCode() {
            return pinCode;
        }

        public void setPinCode(String pinCode) {
            this.pinCode = pinCode;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getFaxNo() {
            return faxNo;
        }

        public void setFaxNo(String faxNo) {
            this.faxNo = faxNo;
        }

        public String getTelephoneNo() {
            return telephoneNo;
        }

        public void setTelephoneNo(String telephoneNo) {
            this.telephoneNo = telephoneNo;
        }

        public String getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getEccNo() {
            return eccNo;
        }

        public void setEccNo(String eccNo) {
            this.eccNo = eccNo;
        }

        public String getCommisionRate() {
            return commisionRate;
        }

        public void setCommisionRate(String commisionRate) {
            this.commisionRate = commisionRate;
        }

        public String getRangeNo() {
            return rangeNo;
        }

        public void setRangeNo(String rangeNo) {
            this.rangeNo = rangeNo;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getRangeAddress() {
            return rangeAddress;
        }

        public void setRangeAddress(String rangeAddress) {
            this.rangeAddress = rangeAddress;
        }
    }

    public static class BankDetailsModel {

        private Long id;
        private String accountType;
        private String ifscCode;
        private String accountNo;
        private String bankName;
        private String swiftNo;
        private String branchName;
        private String bankAddress;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getSwiftNo() {
            return swiftNo;
        }

        public void setSwiftNo(String swiftNo) {
            this.swiftNo = swiftNo;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

    }

    public static class CreditDetailsModel {
        private Long id;
        private String creditLimit;
        private String creditDays;
        private String creditWorthiness;
        private Double interestRate;
        private String remarks;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCreditLimit() {
            return creditLimit;
        }

        public void setCreditLimit(String creditLimit) {
            this.creditLimit = creditLimit;
        }

        public String getCreditDays() {
            return creditDays;
        }

        public void setCreditDays(String creditDays) {
            this.creditDays = creditDays;
        }

        public String getCreditWorthiness() {
            return creditWorthiness;
        }

        public void setCreditWorthiness(String creditWorthiness) {
            this.creditWorthiness = creditWorthiness;
        }

        public Double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(Double interestRate) {
            this.interestRate = interestRate;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

}
