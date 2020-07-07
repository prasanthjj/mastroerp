package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.PartyRequestModel;

import java.util.List;
import java.util.Set;

public interface PartyService {

    List<Party> getAllPartys();

    Party getPartyById(Long id);

    void saveOrUpdateParty(PartyRequestModel partyRequestModel);

    void deleteParty(Long id);

    Set<ContactDetails> saveOrUpdatePartyContactDetails(PartyRequestModel partyRequestModel, Party party);

    Set<BankDetails> saveOrUpdatePartyBankDetails(PartyRequestModel partyRequestModel, Party party);

    Set<BillingDetails> saveOrUpdatePartyBillingDetails(PartyRequestModel partyRequestModel, Party party);

    Set<CreditDetails> saveOrUpdatePartyCreditDetails(PartyRequestModel partyRequestModel, Party party);

    void saveOrUpdatePartyProducts(Party party,Set<Product> products);

}
