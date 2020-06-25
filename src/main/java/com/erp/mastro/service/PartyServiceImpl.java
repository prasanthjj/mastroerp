package com.erp.mastro.service;

import com.erp.mastro.entities.*;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PartyServiceImpl implements PartyService {

    @Autowired
    private PartyRepository partyRepository;

    public List<Party> getAllPartys()
    {
        List<Party> partyList = new ArrayList<Party>();
        partyRepository.findAll().forEach(party ->partyList.add(party));
        return partyList;
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id).get();
    }

    public void saveOrUpdateParty(Party party) {
        partyRepository.save(party);
    }

    public void deleteParty(Long id) {

        partyRepository.deleteById(id);
    }

    public void saveOrUpdatePartyContactDetails(Party party, Set<ContactDetails> contactDetails) {
        party.setContactDetails(contactDetails);
        partyRepository.save(party);
    }

    public void saveOrUpdatePartyBankDetails(Party party, Set<BankDetails> bankDetails) {
        party.setBankDetails(bankDetails);
        partyRepository.save(party);
    }

    public void saveOrUpdatePartyBillingDetails(Party party, Set<BillingDetails> billingDetails) {
        party.setBillingDetails(billingDetails);
        partyRepository.save(party);
    }

    public void saveOrUpdatePartyCreditDetails(Party party, Set<CreditDetails> creditDetails) {
        party.setCreditDetails(creditDetails);
        partyRepository.save(party);
    }

    public void saveOrUpdatePartyProducts(Party party,Set<Product> products) {
        party.setProducts(products);
        partyRepository.save(party);
    }

}
