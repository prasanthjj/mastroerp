package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.HSN;
import com.erp.mastro.model.request.HSNRequestModel;

import java.util.List;

public interface HSNService {

    List<HSN> getAllHSN();

    HSN getHSNById(Long id);

    void saveOrUpdateHSN(HSNRequestModel hsnRequestModel);

    void deleteHSN(Long id);

    public void deleteHsnDetails(Long id);


}
