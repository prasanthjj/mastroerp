package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.StockDetails;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import com.erp.mastro.repository.StockRepository;
import com.erp.mastro.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    /**
     * Method to get a stock by id
     *
     * @param id
     * @return
     */

    public StockDetails getStockById(Long id) {
        StockDetails stockDetails = new StockDetails();
        if (id != null) {
            MastroLogUtils.info(StockService.class, "Going to get all stock by id : {}" + id);
            stockDetails = stockRepository.findById(id).get();
        }
        return stockDetails;
    }

    /**
     * @param stockRequestModel
     * @return
     * @throws ModelNotFoundException
     */

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateStockDetails(StockRequestModel stockRequestModel) throws ModelNotFoundException {
        StockDetails stockDetails = new StockDetails();
        if (stockRequestModel == null) {
            throw new ModelNotFoundException("StockRequest Model is empty");
        } else {
            if (stockRequestModel.getId() == null) {
                MastroLogUtils.info(StockService.class, "Going to add stock : {}");
                stockDetails.setOpeningStock(stockRequestModel.getOpeningStock());
                stockDetails.setCurrentStock(stockRequestModel.getCurrentStock());
                stockDetails.setReorderLevel(stockRequestModel.getReorderLevel());
                stockDetails.setRejectedStock(stockRequestModel.getRejectedStock());
                stockDetails.setReserveStock(stockRequestModel.getReserveStock());
                stockDetails.setMinimumOrderQuantity(stockRequestModel.getMinimumOrderQuantity());
                stockDetails.setMinimumLeadTime(stockRequestModel.getMinimumLeadTime());
                stockDetails.setMaximumLeadTime(stockRequestModel.getMaximumLeadTime());
                stockDetails.setMinimumStockQuantity(stockRequestModel.getMinimumStockQuantity());
                stockDetails.setMaximumStockQuantity(stockRequestModel.getMaximumStockQuantity());

            }
        }
        stockRepository.save(stockDetails);

    }

}
