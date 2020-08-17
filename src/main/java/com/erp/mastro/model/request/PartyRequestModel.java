package com.erp.mastro.model.request;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.entities.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class PartyRequestModel {

    private Long id;
    private String partyType;
    private String partyCode;
    private String partyName;
    private String status;
    private String paymentTerms;
    private String categoryType;
    private Date partyDate;
    private String spartyDate;
    private String oldReferCode;
    private String relationshipMananger;
    private List<ContactDetailsModel> contactDetailsModelList = new ArrayList<>();
    private List<BillingDetailsModel> billingDetailsModelList = new ArrayList<>();
    private List<BankDetailsModel> bankDetailsModelList = new ArrayList<>();
    private List<CreditDetailsModel> creditDetailsModelList = new ArrayList<>();
    private Long industryid;
    private String partysname;

    public PartyRequestModel() {

    }

    public PartyRequestModel(Party party) {
        if (party != null) {
            this.id = party.getId();
            this.partyType = party.getPartyType();
            this.partyCode = party.getPartyCode();
            this.partyName = party.getPartyName();
            this.status = party.getStatus();
            this.paymentTerms = party.getPaymentTerms();
            this.categoryType = party.getCategoryType();
            this.oldReferCode = party.getOldReferCode();
            this.relationshipMananger = party.getRelationshipMananger();
            party.getContactDetails().parallelStream().forEach(x -> this.contactDetailsModelList.add(new ContactDetailsModel(x)));
            party.getBillingDetails().parallelStream().forEach(x -> this.billingDetailsModelList.add(new BillingDetailsModel(x)));
            party.getBankDetails().parallelStream().forEach(x -> this.bankDetailsModelList.add(new BankDetailsModel(x)));
            party.getCreditDetails().parallelStream().forEach(x -> this.creditDetailsModelList.add(new CreditDetailsModel(x)));
            if (party.getIndustryType() != null) {
                this.industryid = party.getIndustryType().getId();
            }

            if (party.getPartyDate() != null) {
                this.partyDate = party.getPartyDate();
            }
        }
    }

    public Date getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(Date partyDate) {
        this.partyDate = partyDate;
    }

    public String getSpartyDate() {
        return MastroApplicationUtils.getStringFromDate(spartyDate, getPartyDate());
    }

    public void setSpartyDate(String spartyDate) {
        this.spartyDate = spartyDate;
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class ContactDetailsModel {

        private Long id;
        private String contactPersonName;
        private String designation;
        private String department;
        private String telephoneNo;
        private String altTelephoneNo;
        private String address;
        private String mobileNo;
        private String altMobileNo;
        private String faxNo;
        private String emailId;

        public ContactDetailsModel() {

        }

        public ContactDetailsModel(ContactDetails contactDetails) {
            this.id = contactDetails.getId();
            this.contactPersonName = contactDetails.getContactPersonName();
            this.designation = contactDetails.getDesignation();
            this.department = contactDetails.getDepartment();
            this.telephoneNo = contactDetails.getTelephoneNo();
            this.altTelephoneNo = contactDetails.getAltTelephoneNo();
            this.address = contactDetails.getAddress();
            this.mobileNo = contactDetails.getMobileNo();
            this.altMobileNo = contactDetails.getAltMobileNo();
            this.faxNo = contactDetails.getFaxNo();
            this.emailId = contactDetails.getEmailId();

        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class BillingDetailsModel {

        private Long id;
        private String type;
        private String country;
        private String state;
        private String street;
        private String city;
        private String pinCode;
        private String designation;
        private String faxNo;
        private String telephoneNo;
        private String contactPersonName;
        private String emailId;

        public BillingDetailsModel() {

        }

        public BillingDetailsModel(BillingDetails billingDetails) {
            this.id = billingDetails.getId();
            this.type = billingDetails.getType();
            this.country = billingDetails.getCountry();
            this.state = billingDetails.getState();
            this.street = billingDetails.getStreet();
            this.city = billingDetails.getCity();
            this.pinCode = billingDetails.getPinCode();
            this.designation = billingDetails.getDesignation();
            this.faxNo = billingDetails.getFaxNo();
            this.telephoneNo = billingDetails.getTelephoneNo();
            this.contactPersonName = billingDetails.getContactPersonName();
            this.emailId = billingDetails.getEmailId();
        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class BankDetailsModel {

        private Long id;
        private String ifscCode;
        private String accountNo;
        private String bankName;
        private String branchName;
        private String bankAddress;

        public BankDetailsModel() {

        }

        public BankDetailsModel(BankDetails bankDetails) {
            this.id = bankDetails.getId();
            this.ifscCode = bankDetails.getIfscCode();
            this.accountNo = bankDetails.getAccountNo();
            this.bankName = bankDetails.getBankName();
            this.branchName = bankDetails.getBranchName();
            this.bankAddress = bankDetails.getBankAddress();
        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class CreditDetailsModel {
        private Long id;
        private String creditLimit;
        private String creditDays;
        private String creditWorthiness;
        private Double interestRate;
        private String remarks;
        private Long branchId;
        private String branchName;

        public CreditDetailsModel() {

        }

        public CreditDetailsModel(CreditDetails creditDetails) {
            this.id = creditDetails.getId();
            this.creditLimit = creditDetails.getCreditLimit();
            this.creditDays = creditDetails.getCreditDays();
            this.creditWorthiness = creditDetails.getCreditWorthiness();
            this.interestRate = creditDetails.getInterestRate();
            this.remarks = creditDetails.getRemarks();
            this.branchId = creditDetails.getBranch().getId();
            this.branchName = creditDetails.getBranch().getBranchName();
        }

    }

}
