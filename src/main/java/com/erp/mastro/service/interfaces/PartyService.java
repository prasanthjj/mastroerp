package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.*;

import java.util.List;
import java.util.Set;

public interface PartyService {

    List<Party> getAllPartys();

    Party getPartyById(Long id);

    void saveOrUpdateParty(Party party);

    void deleteParty(Long id);

    void saveOrUpdatePartyContactDetails(Party party, Set<ContactDetails> contactDetails);

    void saveOrUpdatePartyBankDetails(Party party, Set<BankDetails> bankDetails);

    void saveOrUpdatePartyBillingDetails(Party party, Set<BillingDetails> billingDetails);

    void saveOrUpdatePartyCreditDetails(Party party, Set<CreditDetails> creditDetails);

    void saveOrUpdatePartyProducts(Party party,Set<Product> products);

}
