package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.ProductPartyRateRelation;

import java.util.List;

public interface PartyRateService {

    List<ProductPartyRateRelation> getAllPartyRateRelation();

    ProductPartyRateRelation getPartyRateRelationsById(Long id);

    void saveOrUpdatePartyRateRelation(ProductPartyRateRelation productPartyRateRelation);

    void deletePartyRateRelation(Long id);
}
