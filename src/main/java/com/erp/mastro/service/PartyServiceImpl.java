package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndustryTypeRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.repository.BranchRepository;
import com.erp.mastro.repository.IndustryTypeRepository;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.PartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
/**
 * Service class for party
 */

public class PartyServiceImpl implements PartyService {

    private Logger logger = LoggerFactory.getLogger(PartyServiceImpl.class);

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private IndustryTypeRepository industryTypeRepository;

    @Autowired
    private BranchRepository branchRepository;

    public List<Party> getAllPartys() {
        List<Party> partyList = new ArrayList<Party>();
        partyRepository.findAll().forEach(party -> partyList.add(party));
        return partyList;
    }

    /**
     * * method to get party accroding to id
     * *
     *
     * @param id
     * @return
     */

    public Party getPartyById(Long id) {
        return partyRepository.findById(id).get();
    }

    public List<IndustryType> getAllIndustryType() {
        List<IndustryType> industryTypes = new ArrayList<>();
        industryTypeRepository.findAll().forEach(industryType -> industryTypes.add(industryType));
        return industryTypes;
    }

    /**
     * ** method to get industry according to id
     * *
     *
     * @param id
     * @return
     */

    public IndustryType getIndustryTypeById(Long id) {
        return industryTypeRepository.findById(id).get();
    }

    /**
     * save and edit party
     *
     * @param partyRequestModel
     * @return the party
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Party saveOrUpdateParty(PartyRequestModel partyRequestModel) throws ModelNotFoundException {

        Party party = new Party();

        if (partyRequestModel == null) {
            throw new ModelNotFoundException("PartyRequestModel model is empty");
        } else {
            if (partyRequestModel.getId() == null) {

                MastroLogUtils.info(PartyService.class, "Going to Add Party  {}" + partyRequestModel.toString());

                party.setPartyType(partyRequestModel.getPartyType());
                party.setPartyCode(partyRequestModel.getPartyCode());
                party.setPartyName(partyRequestModel.getPartyName());
                party.setStatus(partyRequestModel.getStatus());
                party.setPaymentTerms(partyRequestModel.getPaymentTerms());
                party.setCategoryType(partyRequestModel.getCategoryType());
                party.setPartyDate(partyRequestModel.getPartyDate());
                party.setOldReferCode(partyRequestModel.getOldReferCode());
                party.setRelationshipMananger(partyRequestModel.getRelationshipMananger());
                party.setIndustryType(industryTypeRepository.findById(partyRequestModel.getIndustryid()).get());
                Set<ContactDetails> contactDetails = saveOrUpdatePartyContactDetails(partyRequestModel, party);
                party.setContactDetails(contactDetails);
                Set<BankDetails> bankDetails = saveOrUpdatePartyBankDetails(partyRequestModel, party);
                party.setBankDetails(bankDetails);
                Set<BillingDetails> billingDetails = saveOrUpdatePartyBillingDetails(partyRequestModel, party);
                party.setBillingDetails(billingDetails);
                Set<CreditDetails> creditDetails = saveOrUpdatePartyCreditDetails(partyRequestModel, party);
                party.setCreditDetails(creditDetails);
                partyRepository.save(party);

                MastroLogUtils.info(PartyService.class, "Added" + party.getPartyName() + "successfully");

            } else {
                MastroLogUtils.info(PartyService.class, "Going to edit party {}" + partyRequestModel.toString());

                party = partyRepository.findById(partyRequestModel.getId()).get();
                party.setPartyType(partyRequestModel.getPartyType());
                party.setPartyCode(partyRequestModel.getPartyCode());
                party.setPartyName(partyRequestModel.getPartyName());
                party.setStatus(partyRequestModel.getStatus());
                party.setPaymentTerms(partyRequestModel.getPaymentTerms());
                party.setCategoryType(partyRequestModel.getCategoryType());
                party.setPartyDate(partyRequestModel.getPartyDate());
                party.setOldReferCode(partyRequestModel.getOldReferCode());
                party.setRelationshipMananger(partyRequestModel.getRelationshipMananger());

                partyRepository.save(party);

                MastroLogUtils.info(PartyService.class, "Updated" + party.getPartyName() + "successfully");
            }
            return party;
        }
    }

    /**
     * save industrytype
     *
     * @param industryTypeRequestModel
     * @throws ModelNotFoundException
     */

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateIndustryType(IndustryTypeRequestModel industryTypeRequestModel) throws ModelNotFoundException {
        IndustryType industryType = new IndustryType();
        if (industryTypeRequestModel == null) {
            throw new ModelNotFoundException("IndustryType Request Model is empty");

        } else {
            if (industryTypeRequestModel.getId() == null) {
                MastroLogUtils.info(PartyService.class, "Going to Add IndustryType  {}" + industryTypeRequestModel.toString());

                industryType.setIndustryType(industryTypeRequestModel.getIndustryType());
                industryTypeRepository.save(industryType);
            }
        }
    }

    /**
     * Save and edit contact details
     *
     * @param partyRequestModel
     * @param party
     * @return
     */

    @Transactional(rollbackOn = {Exception.class})
    public Set<ContactDetails> saveOrUpdatePartyContactDetails(PartyRequestModel partyRequestModel, Party party) throws ModelNotFoundException {

        MastroLogUtils.info(PartyService.class, "Going to save party contactDetails {}" + partyRequestModel.toString());
        Set<ContactDetails> contactDetailsSet = new HashSet<>();

        if (partyRequestModel.getContactDetailsModelList().isEmpty() == false) {

            partyRequestModel.getContactDetailsModelList().parallelStream()
                    .forEach(x -> {
                        ContactDetails contactDetails;
                        if (!containsInList(x.getId(), party.getContactDetails().stream().filter(partydata -> (null != partydata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            contactDetails = new ContactDetails();
                            contactDetails.setContactPersonName(x.getContactPersonName());
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
                        } else {
                            contactDetails = party.getContactDetails().stream().filter(partydata -> (null != partydata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            contactDetails.setContactPersonName(x.getContactPersonName());
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

                        }
                    });

        } else {
            throw new ModelNotFoundException("PartyRequest model is empty");
        }
        removeBlankContactDetails(contactDetailsSet);
        return contactDetailsSet;
    }

    /**
     * Save and edit bank details
     *
     * @param partyRequestModel
     * @param party
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public Set<BankDetails> saveOrUpdatePartyBankDetails(PartyRequestModel partyRequestModel, Party party) throws ModelNotFoundException {

        MastroLogUtils.info(PartyService.class, "Going to save party bankdetails {}" + partyRequestModel.toString());
        Set<BankDetails> bankDetailsSet = new HashSet<>();

        if (partyRequestModel.getBankDetailsModelList().isEmpty() == false) {
            partyRequestModel.getBankDetailsModelList().parallelStream()
                    .forEach(x -> {
                        BankDetails bankDetails;
                        if (!containsInList(x.getId(), party.getBankDetails().stream().filter(partyBankdata -> (null != partyBankdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            bankDetails = new BankDetails();
                            bankDetails.setIfscCode(x.getIfscCode());
                            bankDetails.setAccountNo(x.getAccountNo());
                            bankDetails.setBankName(x.getBankName());
                            bankDetails.setBranchName(x.getBranchName());
                            bankDetails.setBankAddress(x.getBankAddress());
                            bankDetailsSet.add(bankDetails);
                        } else {
                            bankDetails = party.getBankDetails().stream().filter(partyBankdata -> (null != partyBankdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            bankDetails.setIfscCode(x.getIfscCode());
                            bankDetails.setAccountNo(x.getAccountNo());
                            bankDetails.setBankName(x.getBankName());
                            bankDetails.setBranchName(x.getBranchName());
                            bankDetails.setBankAddress(x.getBankAddress());
                            bankDetailsSet.add(bankDetails);
                        }
                    });
        }
        removeBlankBankDetails(bankDetailsSet);
        return bankDetailsSet;

    }

    /**
     * Save and edit billing details
     *
     * @param partyRequestModel
     * @param party
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public Set<BillingDetails> saveOrUpdatePartyBillingDetails(PartyRequestModel partyRequestModel, Party party) {

        MastroLogUtils.info(PartyService.class, "Going to save party billing details {}" + partyRequestModel.toString());
        Set<BillingDetails> billingDetailsSet = new HashSet<>();

        if (partyRequestModel.getBillingDetailsModelList().isEmpty() == false) {
            partyRequestModel.getBillingDetailsModelList().parallelStream()
                    .forEach(x -> {
                        BillingDetails billingDetails;
                        if (!containsInList(x.getId(), party.getBillingDetails().stream().filter(partyBillingdata -> (null != partyBillingdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            billingDetails = new BillingDetails();
                            billingDetails.setType(x.getType());
                            billingDetails.setCountry(x.getCountry());
                            billingDetails.setState(x.getState());
                            billingDetails.setStreet(x.getStreet());
                            billingDetails.setCity(x.getCity());
                            billingDetails.setPinCode(x.getPinCode());
                            billingDetails.setDesignation(x.getDesignation());
                            billingDetails.setFaxNo(x.getFaxNo());
                            billingDetails.setTelephoneNo(x.getTelephoneNo());
                            billingDetails.setContactPersonName(x.getContactPersonName());
                            billingDetails.setEmailId(x.getEmailId());
                            billingDetailsSet.add(billingDetails);
                        } else {
                            billingDetails = party.getBillingDetails().stream().filter(partyBillingdata -> (null != partyBillingdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            billingDetails.setType(x.getType());
                            billingDetails.setCountry(x.getCountry());
                            billingDetails.setState(x.getState());
                            billingDetails.setStreet(x.getStreet());
                            billingDetails.setCity(x.getCity());
                            billingDetails.setPinCode(x.getPinCode());
                            billingDetails.setDesignation(x.getDesignation());
                            billingDetails.setFaxNo(x.getFaxNo());
                            billingDetails.setTelephoneNo(x.getTelephoneNo());
                            billingDetails.setContactPersonName(x.getContactPersonName());
                            billingDetails.setEmailId(x.getEmailId());
                            billingDetailsSet.add(billingDetails);
                        }
                    });
        }
        removeBlankBillingDetails(billingDetailsSet);
        return billingDetailsSet;
    }

    /**
     * Save and edit Credit Details
     *
     * @param partyRequestModel
     * @param party
     * @return
     */


    @Transactional(rollbackOn = {Exception.class})
    public Set<CreditDetails> saveOrUpdatePartyCreditDetails(PartyRequestModel partyRequestModel, Party party) {

        MastroLogUtils.info(PartyService.class, "Going to save party credit details {}" + partyRequestModel.toString());
        Set<CreditDetails> creditDetailsSet = new HashSet<>();

        if (partyRequestModel.getCreditDetailsModelList().isEmpty() == false) {
            partyRequestModel.getCreditDetailsModelList().parallelStream()
                    .forEach(x -> {
                        CreditDetails creditDetails;
                        if (!containsInList(x.getId(), party.getCreditDetails().stream().filter(partyCreditdata -> (null != partyCreditdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            creditDetails = new CreditDetails();
                            creditDetails.setBranch(branchRepository.findById(Long.parseLong(x.getBranchId().toString())).get());
                            creditDetails.setCreditLimit(x.getCreditLimit());
                            creditDetails.setCreditDays(x.getCreditDays());
                            creditDetails.setCreditWorthiness(x.getCreditWorthiness());
                            creditDetails.setInterestRate(x.getInterestRate());
                            creditDetails.setRemarks(x.getRemarks());
                            creditDetailsSet.add(creditDetails);
                        } else {
                            creditDetails = party.getCreditDetails().stream().filter(partyCreditdata -> (null != partyCreditdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            creditDetails.setCreditLimit(x.getCreditLimit());
                            creditDetails.setCreditDays(x.getCreditDays());
                            creditDetails.setCreditWorthiness(x.getCreditWorthiness());
                            creditDetails.setInterestRate(x.getInterestRate());
                            creditDetails.setRemarks(x.getRemarks());
                            creditDetailsSet.add(creditDetails);
                        }
                    });
        }
        removeBlankCreditDetails(creditDetailsSet);
        return creditDetailsSet;
    }

    public void saveOrUpdatePartyProducts(Party party, Set<Product> products) {
        party.setProducts(products);
        partyRepository.save(party);
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

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    /**
     * Remove blank contactdetails
     *
     * @param contactDetails
     */
    private void removeBlankContactDetails(Set<ContactDetails> contactDetails) {
        contactDetails.removeIf(x -> x.getContactPersonName() == null);
    }

    /**
     * Remove blank bankDetails
     *
     * @param bankDetails
     */
    private void removeBlankBankDetails(Set<BankDetails> bankDetails) {
        bankDetails.removeIf(x -> x.getAccountNo() == null);
    }

    /**
     * Remove blank billing details
     *
     * @param billingDetails
     */

    private void removeBlankBillingDetails(Set<BillingDetails> billingDetails) {
        billingDetails.removeIf(x -> x.getType() == null);
    }

    /**
     * Remove blank credit details
     *
     * @param creditDetails
     */
    private void removeBlankCreditDetails(Set<CreditDetails> creditDetails) {
        creditDetails.removeIf(x -> x.getCreditDays() == null);
    }

}
