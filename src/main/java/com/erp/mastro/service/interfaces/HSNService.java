package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.HSN;

import java.util.List;

public interface HSNService {

    List<HSN> getAllHSN();

    HSN getHSNById(Long id);

    void saveOrUpdateHSN(HSN hsn);

    void deleteHSN(Long id);


}
