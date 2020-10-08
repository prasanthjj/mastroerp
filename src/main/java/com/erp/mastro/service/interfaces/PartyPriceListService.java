package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.PartyPriceList;

import java.util.List;

public interface PartyPriceListService {

    List<PartyPriceList> getAllPartyRateRelation();

    PartyPriceList getPartyRateRelationsById(Long id);

    void saveOrUpdatePartyRateRelation(PartyPriceList partyPriceList);

    void deletePartyRateRelation(Long id);
}
