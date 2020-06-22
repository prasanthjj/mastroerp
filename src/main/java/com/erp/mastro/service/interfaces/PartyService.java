package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.SubCategory;

import java.util.List;
import java.util.Set;

public interface PartyService {

    List<Party> getAllPartys();

    Party getPartyById(Long id);

    void saveOrUpdateParty(Party party);

    void deleteParty(Long id);

}
