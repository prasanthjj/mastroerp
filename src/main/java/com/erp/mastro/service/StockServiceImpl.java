package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.StockDetails;
import com.erp.mastro.entities.User;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import com.erp.mastro.repository.ProductRepository;
import com.erp.mastro.repository.StockRepository;
import com.erp.mastro.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserController userController;

    public List<StockDetails> getAllStockDetails() {
        List<StockDetails> stockDetailsList = new ArrayList<>();
        stockRepository.findAll().forEach(stockDetails -> stockDetailsList.add(stockDetails));
        return stockDetailsList;
    }

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
    public StockDetails saveOrUpdateStockDetails(StockRequestModel stockRequestModel) throws ModelNotFoundException {
        StockDetails stockDetails = new StockDetails();
        if (stockRequestModel == null) {
            throw new ModelNotFoundException("StockRequest Model is empty");
        } else {
            if (stockRequestModel.getId() == null) {
                MastroLogUtils.info(StockService.class, "Going to add stock : {}" + stockRequestModel.getId());
                Product product = productRepository.findById(stockRequestModel.getProductId()).get();
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
                if (product.getId() != null) {
                    stockDetails.setProduct(product);
                    stockDetails.setUom(product.getUom());
                    stockDetails.setRate(product.getBasePrice());
                }
                User userDetails = userController.getCurrentUser();
                List<Branch> branchList = new ArrayList<>();
                Branch currentBranch = null;
                if (null != userDetails) {
                    for (Branch branch : userDetails.getBranch()) {
                        branchList.add(branch);
                    }
                    if (userDetails.getUserSelectedBranch() != null && userDetails.getUserSelectedBranch().getCurrentBranch() != null) {
                        currentBranch = userDetails.getUserSelectedBranch().getCurrentBranch();
                    }
                }
                stockDetails.setBranch(currentBranch);
                stockRepository.save(stockDetails);
            } else {
                MastroLogUtils.info(StockService.class, "Going to edit stock : {}" + stockRequestModel.toString());
                stockDetails = stockRepository.findById(stockRequestModel.getId()).get();
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

        return stockDetails;
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    /**
     * Method to delete StockDetails by id
     *
     * @param id
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public StockDetails deleteStockDetails(Long id) {
        MastroLogUtils.info(StockService.class, "Going to delete StockDetails by id : {}" + id);
        StockDetails stockDetails = new StockDetails();
        if (id != null) {
            stockDetails = getStockById(id);
            stockDetails.setStockDeleteStatus(1);
            stockRepository.save(stockDetails);
        }
        return stockDetails;
    }

}