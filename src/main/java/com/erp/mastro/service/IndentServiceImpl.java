package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.Indent;
import com.erp.mastro.entities.ItemStockDetails;
import com.erp.mastro.entities.Stock;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentModel;
import com.erp.mastro.repository.IndentRepository;
import com.erp.mastro.repository.StockRepository;
import com.erp.mastro.service.interfaces.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IndentServiceImpl implements IndentService {

    @Autowired
    private IndentRepository indentRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private StockRepository stockRepository;

    /**
     * method to get all indents
     *
     * @return indent list
     */
    public List<Indent> getAllIndents() {
        List<Indent> indentList = new ArrayList<Indent>();
        indentRepository.findAll().forEach(indent -> indentList.add(indent));
        return indentList;
    }

    public List<Stock> getAllStocks() {
        List<Stock> stockList = new ArrayList<Stock>();
        stockRepository.findAll().forEach(stock -> stockList.add(stock));
        return stockList;
    }

    /**
     * Method to get indent by id
     *
     * @param id
     * @return the indent
     */
    public Indent getIndentById(Long id) {

        Indent indent = new Indent();
        if (id != null) {
            MastroLogUtils.info(IndentService.class, "Going to getIndentBy Id : {}" + id);
            indent = indentRepository.findById(id).get();
        }
        return indent;
    }

    /**
     * Method to create indent
     *
     * @param indentModel
     * @return indent
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Indent createIndent(IndentModel indentModel) throws ModelNotFoundException {

        Indent indent = new Indent();

        if (indentModel == null) {
            throw new ModelNotFoundException("Indent model is empty");
        } else {
            if (indentModel.getId() == null) {

                MastroLogUtils.info(IndentModel.class, "Going to create indent{}" + indentModel.toString());
                indent.setIndentPriority(indentModel.getIndentPriority());
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                indent.setBranch(currentBranch);

                ItemStockDetails itemStockDetails = new ItemStockDetails();
                Set<Stock> stockSet = getAllStocks().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId() == stockData.getBranch().getId()))
                        .filter(stockData -> (indentModel.getProductId() == stockData.getProduct().getId()))
                        .collect(Collectors.toSet());
                itemStockDetails.setStock(stockSet.stream().findFirst().get());
                indent.getItemStockDetailsSet().add(itemStockDetails);
                indentRepository.save(indent);

                MastroLogUtils.info(IndentService.class, "create indent with" + indent.getIndentPriority() + " succesfully.");

            } else {
                MastroLogUtils.info(IndentService.class, "Going to edit indent  {}" + indentModel.toString());

                indent = indentRepository.findById(indentModel.getId()).get();
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                ItemStockDetails itemStockDetails = new ItemStockDetails();
                Set<Stock> stockSet = getAllStocks().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId() == stockData.getBranch().getId()))
                        .filter(stockData -> (indentModel.getProductId() == stockData.getProduct().getId()))
                        .collect(Collectors.toSet());
                itemStockDetails.setStock(stockSet.stream().findFirst().get());
                indent.getItemStockDetailsSet().add(itemStockDetails);
                indentRepository.save(indent);
            }
            return indent;
        }

    }

}
