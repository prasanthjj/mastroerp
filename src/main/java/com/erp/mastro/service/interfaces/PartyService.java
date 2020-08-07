package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndustryTypeRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;

import java.util.List;
import java.util.Set;

public interface PartyService {

    List<Party> getAllPartys();

    Party getPartyById(Long id);

    List<IndustryType> getAllIndustryType();

    IndustryType getIndustryTypeById(Long id);

    Party saveOrUpdateParty(PartyRequestModel partyRequestModel, String[] branchids, String[] creditLimits, String[] creditDays, String[] creditWorthiness, String[] interestRates, String[] remarks) throws ModelNotFoundException;

    void deleteParty(Long id);

     void activateOrDeactivateParty(Long id);

    Set<ContactDetails> saveOrUpdatePartyContactDetails(PartyRequestModel partyRequestModel, Party party) throws ModelNotFoundException;

    Set<BankDetails> saveOrUpdatePartyBankDetails(PartyRequestModel partyRequestModel, Party party) throws ModelNotFoundException;

    Set<BillingDetails> saveOrUpdatePartyBillingDetails(PartyRequestModel partyRequestModel, Party party);

    Set<CreditDetails> saveOrUpdatePartyCreditDetails(PartyRequestModel partyRequestModel, String[] branchids, String[] creditLimits, String[] creditDays, String[] creditWorthiness, String[] interestRates, String[] remarks, Party party);

    void saveOrUpdateIndustryType(IndustryTypeRequestModel industryTypeRequestModel) throws ModelNotFoundException;

    void saveOrUpdatePartyProducts(Party party, Set<Product> products);

}
