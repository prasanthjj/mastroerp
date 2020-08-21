package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.StockDetails;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import org.springframework.stereotype.Service;

@Service
public interface StockService {

    StockDetails getStockById(Long id);

    void saveOrUpdateStockDetails(StockRequestModel stockRequestModel) throws ModelNotFoundException;

}
