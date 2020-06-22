package com.erp.mastro.service;


import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.entities.HSN;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HSNServiceImpl implements HSNService {

    @Autowired
    private HSNRepository hsnRepository;


    public List<HSN> getAllHSN()
    {
        List<HSN> hsnList = new ArrayList<HSN>();
        hsnRepository.findAll().forEach(hsn ->hsnList.add(hsn));
        return hsnList;
    }

    public HSN getHSNById(Long id) { return hsnRepository.findById(id).get(); }


    public void saveOrUpdateHSN(HSN hsn) {hsnRepository.save(hsn); }

    public void deleteHSN(Long id) {hsnRepository.deleteById(id); }
}
