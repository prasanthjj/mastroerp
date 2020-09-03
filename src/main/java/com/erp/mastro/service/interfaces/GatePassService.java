package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.GatePass;
import com.erp.mastro.model.request.GatePassRequestModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface GatePassService {

    List<GatePass> getAllGatePass();

    GatePass getGatePassId(Long id);

    void saveOrUpdateGatePass(GatePassRequestModel gatePassRequestModel , String value,String val) throws ParseException;

    public void deleteGatePass(Long id);





}
