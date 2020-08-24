package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.StockDetails;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    List<StockDetails> getAllStockDetails();

    StockDetails getStockById(Long id);

    StockDetails saveOrUpdateStockDetails(StockRequestModel stockRequestModel) throws ModelNotFoundException;

    void deleteStock(Long id);

    StockDetails deleteStockDetails(Long id);

}
