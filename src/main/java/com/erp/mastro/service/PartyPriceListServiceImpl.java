package com.erp.mastro.service;

import com.erp.mastro.entities.PartyPriceList;
import com.erp.mastro.repository.PartyPriceListRepository;
import com.erp.mastro.service.interfaces.PartyPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyPriceListServiceImpl implements PartyPriceListService {

    @Autowired
    private PartyPriceListRepository partyPriceListRepository;

    public List<PartyPriceList> getAllPartyRateRelation() {
        List<PartyPriceList> partyRateRelations = new ArrayList<PartyPriceList>();
        partyPriceListRepository.findAll().forEach(partyRateRelation -> partyRateRelations.add(partyRateRelation));
        return partyRateRelations;
    }

    public PartyPriceList getPartyRateRelationsById(Long id) {
        return partyPriceListRepository.findById(id).get();
    }

    public void saveOrUpdatePartyRateRelation(PartyPriceList partyPriceList) {
        partyPriceListRepository.save(partyPriceList);
    }

    public void deletePartyRateRelation(Long id)
    {
        partyPriceListRepository.deleteById(id);
    }
}
