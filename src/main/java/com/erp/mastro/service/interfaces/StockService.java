package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Stock;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {

    List<Stock> getAllStockDetails();

    Stock getStockById(Long id);

    Stock saveOrUpdateStockDetails(StockRequestModel stockRequestModel) throws ModelNotFoundException;

    void deleteStock(Long id);

    Stock deleteStockDetails(Long id);

}
