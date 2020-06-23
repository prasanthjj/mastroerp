package com.erp.mastro.service;

import com.erp.mastro.entities.CreditDetails;
import com.erp.mastro.entities.ProductPartyRateRelation;
import com.erp.mastro.repository.CreditDetailsRepository;
import com.erp.mastro.repository.PartyRateRepository;
import com.erp.mastro.service.interfaces.PartyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyRateServiceImpl implements PartyRateService {

    @Autowired
    private PartyRateRepository partyRateRepository;

    public List<ProductPartyRateRelation> getAllPartyRateRelation()
    {
        List<ProductPartyRateRelation> partyRateRelations = new ArrayList<ProductPartyRateRelation>();
        partyRateRepository.findAll().forEach(partyRateRelation -> partyRateRelations.add(partyRateRelation));
        return partyRateRelations;
    }

    public ProductPartyRateRelation getPartyRateRelationsById(Long id)
    {
        return partyRateRepository.findById(id).get();
    }

    public void saveOrUpdatePartyRateRelation(ProductPartyRateRelation productPartyRateRelation)
    {
        partyRateRepository.save(productPartyRateRelation);
    }

    public void deletePartyRateRelation(Long id)
    {
        partyRateRepository.deleteById(id);
    }
}
