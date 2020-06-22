package com.erp.mastro.service;

import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.dao.ProductRepository;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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

    public Party getPartyById(Long id)
    {
        return partyRepository.findById(id).get();
    }

    public void saveOrUpdateParty(Party party)
    {
        partyRepository.save(party);
    }

    public void deleteParty(Long id)
    {
        partyRepository.deleteById(id);
    }

}
