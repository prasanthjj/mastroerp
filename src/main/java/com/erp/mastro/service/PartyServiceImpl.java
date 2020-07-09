package com.erp.mastro.service;

import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;

    public List<Party> getAllPartys() {
        List<Party> partyList = new ArrayList<Party>();
        partyRepository.findAll().forEach(party -> partyList.add(party));
        return partyList;
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateParty(PartyRequestModel partyRequestModel) {
        if (partyRequestModel.getId() == null) {
            Party party = new Party();
            party.setPartyType(partyRequestModel.getPartyType());
            party.setPartyCode(partyRequestModel.getPartyCode());
            party.setPartyName(partyRequestModel.getPartyName());
            party.setAliasName(partyRequestModel.getAliasName());
            party.setStatus(partyRequestModel.getStatus());
            party.setType(partyRequestModel.getType());
            party.setWebsite(partyRequestModel.getWebsite());
            party.setPaymentTerms(partyRequestModel.getPaymentTerms());
            party.setReferBy(partyRequestModel.getReferBy());
            party.setSource(partyRequestModel.getSource());
            party.setCategoryType(partyRequestModel.getCategoryType());
            party.setTransporterName(partyRequestModel.getTransporterName());
            party.setPartyDate(partyRequestModel.getPartyDate());
            party.setOldReferCode(partyRequestModel.getOldReferCode());
            party.setRelationshipMananger(partyRequestModel.getRelationshipMananger());
            party.setIndustryType(partyRequestModel.getIndustryType());
            party.setEmailId(partyRequestModel.getEmailId());
            party.setTransactionType(partyRequestModel.getTransactionType());
            Set<ContactDetails> contactDetails = saveOrUpdatePartyContactDetails(partyRequestModel, party);
            party.setContactDetails(contactDetails);
            Set<BankDetails> bankDetails = saveOrUpdatePartyBankDetails(partyRequestModel, party);
            party.setBankDetails(bankDetails);
            Set<BillingDetails> billingDetails = saveOrUpdatePartyBillingDetails(partyRequestModel, party);
            party.setBillingDetails(billingDetails);
            Set<CreditDetails> creditDetails = saveOrUpdatePartyCreditDetails(partyRequestModel, party);
            party.setCreditDetails(creditDetails);
            partyRepository.save(party);
        } else {
            Party party = partyRepository.findById(partyRequestModel.getId()).get();
            party.setPartyType(partyRequestModel.getPartyType());
            party.setPartyCode(partyRequestModel.getPartyCode());
            party.setPartyName(partyRequestModel.getPartyName());
            party.setAliasName(partyRequestModel.getAliasName());
            party.setStatus(partyRequestModel.getStatus());
            party.setType(partyRequestModel.getType());
            party.setWebsite(partyRequestModel.getWebsite());
            party.setPaymentTerms(partyRequestModel.getPaymentTerms());
            party.setReferBy(partyRequestModel.getReferBy());
            party.setSource(partyRequestModel.getSource());
            party.setCategoryType(partyRequestModel.getCategoryType());
            party.setTransporterName(partyRequestModel.getTransporterName());
            party.setPartyDate(partyRequestModel.getPartyDate());
            party.setOldReferCode(partyRequestModel.getOldReferCode());
            party.setRelationshipMananger(partyRequestModel.getRelationshipMananger());
            party.setIndustryType(partyRequestModel.getIndustryType());
            party.setEmailId(partyRequestModel.getEmailId());
            party.setTransactionType(partyRequestModel.getTransactionType());
            partyRepository.save(party);

        }
    }

    public void deleteParty(Long id) {

        partyRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deletePartyDetails(Long id) {
        Party party = getPartyById(id);
        party.setPartyDeleteStatus(1);
        partyRepository.save(party);

    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<ContactDetails> saveOrUpdatePartyContactDetails(PartyRequestModel partyRequestModel, Party party) {
        Set<ContactDetails> contactDetailsSet = new HashSet<>();
        party.getContactDetails().clear();
        if (partyRequestModel.getContactDetailsModelList().isEmpty() == false) {
            partyRequestModel.getContactDetailsModelList().parallelStream()
                    .forEach(x -> {
                        ContactDetails contactDetails = new ContactDetails();
                        contactDetails.setContactPersonName(x.getContactPersonName());
                        contactDetails.setContactType(x.getContactType());
                        contactDetails.setDesignation(x.getDesignation());
                        contactDetails.setDepartment(x.getDepartment());
                        contactDetails.setTelephoneNo(x.getTelephoneNo());
                        contactDetails.setAltTelephoneNo(x.getAltTelephoneNo());
                        contactDetails.setAddress(x.getAddress());
                        contactDetails.setMobileNo(x.getMobileNo());
                        contactDetails.setAltMobileNo(x.getAltMobileNo());
                        contactDetails.setFaxNo(x.getFaxNo());
                        contactDetails.setEmailId(x.getEmailId());
                        contactDetailsSet.add(contactDetails);

                    });
        }
        return contactDetailsSet;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<BankDetails> saveOrUpdatePartyBankDetails(PartyRequestModel partyRequestModel, Party party) {
        Set<BankDetails> bankDetailsSet = new HashSet<>();
        party.getBankDetails().clear();
        if (partyRequestModel.getBankDetailsModelList().isEmpty() == false) {
            partyRequestModel.getBankDetailsModelList().parallelStream()
                    .forEach(x -> {
                        BankDetails bankDetails = new BankDetails();
                        bankDetails.setAccountType(x.getAccountType());
                        bankDetails.setIfscCode(x.getIfscCode());
                        bankDetails.setAccountNo(x.getAccountNo());
                        bankDetails.setBankName(x.getBankName());
                        bankDetails.setSwiftNo(x.getSwiftNo());
                        bankDetails.setBranchName(x.getBranchName());
                        bankDetails.setBankAddress(x.getBankAddress());
                        bankDetailsSet.add(bankDetails);
                    });
        }
        return bankDetailsSet;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<BillingDetails> saveOrUpdatePartyBillingDetails(PartyRequestModel partyRequestModel, Party party) {
        Set<BillingDetails> billingDetailsSet = new HashSet<>();
        party.getBillingDetails().clear();
        if (partyRequestModel.getBillingDetailsModelList().isEmpty() == false) {
            partyRequestModel.getBillingDetailsModelList().parallelStream()
                    .forEach(x -> {
                        BillingDetails billingDetails = new BillingDetails();
                        billingDetails.setType(x.getType());
                        billingDetails.setCountry(x.getCountry());
                        billingDetails.setState(x.getState());
                        billingDetails.setStreet(x.getStreet());
                        billingDetails.setCity(x.getCity());
                        billingDetails.setArea(x.getArea());
                        billingDetails.setPinCode(x.getPinCode());
                        billingDetails.setDesignation(x.getDesignation());
                        billingDetails.setFaxNo(x.getFaxNo());
                        billingDetails.setTelephoneNo(x.getTelephoneNo());
                        billingDetails.setContactPersonName(x.getContactPersonName());
                        billingDetails.setGst(x.getGst());
                        billingDetails.setEmailId(x.getEmailId());
                        billingDetails.setEccNo(x.getEccNo());
                        billingDetails.setCommisionRate(x.getCommisionRate());
                        billingDetails.setRangeNo(x.getRangeNo());
                        billingDetails.setDivision(x.getDivision());
                        billingDetails.setRangeAddress(x.getRangeAddress());
                        billingDetailsSet.add(billingDetails);
                    });
        }
        return billingDetailsSet;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<CreditDetails> saveOrUpdatePartyCreditDetails(PartyRequestModel partyRequestModel, Party party) {
        Set<CreditDetails> creditDetailsSet = new HashSet<>();
        party.getCreditDetails().clear();
        if (partyRequestModel.getCreditDetailsModelList().isEmpty() == false) {
            partyRequestModel.getCreditDetailsModelList().parallelStream()
                    .forEach(x -> {
                        CreditDetails creditDetails = new CreditDetails();
                        creditDetails.setCreditLimit(x.getCreditLimit());
                        creditDetails.setCreditDays(x.getCreditDays());
                        creditDetails.setCreditWorthiness(x.getCreditWorthiness());
                        creditDetails.setInterestRate(x.getInterestRate());
                        creditDetails.setRemarks(x.getRemarks());
                        creditDetailsSet.add(creditDetails);
                    });
        }
        return creditDetailsSet;
    }

    public void saveOrUpdatePartyProducts(Party party, Set<Product> products) {
        party.setProducts(products);
        partyRepository.save(party);
    }


}
