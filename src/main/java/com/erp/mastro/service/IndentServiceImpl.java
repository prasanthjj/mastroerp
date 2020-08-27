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
import com.erp.mastro.repository.UOMRepository;
import com.erp.mastro.service.interfaces.AssetService;
import com.erp.mastro.service.interfaces.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndentServiceImpl implements IndentService {

    @Autowired
    private IndentRepository indentRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UOMRepository uomRepository;

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
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (indentModel.getProductId().equals(stockData.getProduct().getId())))
                        .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
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
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (indentModel.getProductId().equals(stockData.getProduct().getId())))
                        .collect(Collectors.toSet());
                itemStockDetails.setStock(stockSet.stream().findFirst().get());
                indent.getItemStockDetailsSet().add(itemStockDetails);
                indentRepository.save(indent);
            }
            return indent;
        }

    }

    /**
     * Method to save or update indent
     *
     * @param indentModel
     * @return indent
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Indent saveOrUpdateIndentItemDetails(IndentModel indentModel) throws ModelNotFoundException {

        Indent indent = new Indent();

        if (indentModel == null) {
            throw new ModelNotFoundException("indentRequestModel model is empty");
        } else {

            MastroLogUtils.info(IndentService.class, "Going to edit indent Items {}" + indentModel.toString());
            indent = indentRepository.findById(indentModel.getId()).get();
            Set<ItemStockDetails> itemStockDetails = saveOrUpdateIndentItems(indentModel, indent);
            indent.setItemStockDetailsSet(itemStockDetails);
            indentRepository.save(indent);

            MastroLogUtils.info(AssetService.class, "Save " + indent.getId() + " succesfully.");

        }
        return indent;

    }

    /**
     * Method to save or update indent item
     *
     * @param indentModel
     * @param indent
     * @return
     * @throws ModelNotFoundException
     */
    private Set<ItemStockDetails> saveOrUpdateIndentItems(IndentModel indentModel, Indent indent) throws ModelNotFoundException {

        MastroLogUtils.info(IndentService.class, "Going to edit indent items  {}" + indentModel.toString());
        Set<ItemStockDetails> itemStockDetailsSet = new HashSet<>();

        if (indentModel.getIndentItemStockDetailsModels().isEmpty() == false) {

            for (IndentModel.IndentItemStockDetailsModel itemStockDetailsModel : indentModel.getIndentItemStockDetailsModels()) {

                if (itemStockDetailsModel.getId() != null) {
                    ItemStockDetails itemStockDetails = indent.getItemStockDetailsSet().stream()
                            .filter(indentItem -> (null != indentItem))
                            .filter(indentItem -> (indentItem.getId().equals(itemStockDetailsModel.getId())))
                            .findFirst().get();

                    itemStockDetails.setQuantityToIndent(itemStockDetailsModel.getQuantityToIndent());
                    itemStockDetails.setSoReferenceNo(itemStockDetailsModel.getSoReferenceNo());
                    if (itemStockDetailsModel.getUomId() != null) {
                        itemStockDetails.setPurchaseUOM(uomRepository.findById(itemStockDetailsModel.getUomId()).get());
                    }
                    String sDate1 = itemStockDetailsModel.getSrequiredByDate();
                    if (!sDate1.equals("")) {
                        Date date1 = null;
                        try {
                            date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        itemStockDetails.setRequiredByDate(date1);
                    } else if (sDate1.equals("")) {
                        Date date1 = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(date1);
                        c.add(Calendar.DATE, 7);
                        Date date1PlusSeven = c.getTime();
                        itemStockDetails.setRequiredByDate(date1PlusSeven);
                    }
                    itemStockDetailsSet.add(itemStockDetails);
                }
            }

        } else {
            throw new ModelNotFoundException("Indent item model is empty");
        }

        return itemStockDetailsSet;

    }

    /**
     * Method to remove indent item
     *
     * @param indentId
     * @param indentItemId
     */
    public void removeIndentItem(Long indentId, Long indentItemId) {

        Indent indent = getIndentById(indentId);
        if (indent.getItemStockDetailsSet() != null) {
            MastroLogUtils.info(IndentService.class, "Going to remove indent item  {}" + indentItemId);
            Set<ItemStockDetails> itemStockDetails = indent.getItemStockDetailsSet();
            Iterator<ItemStockDetails> itemStockDetailSet = itemStockDetails.iterator();
            for (Iterator<ItemStockDetails> itemStockDetailsIterator = itemStockDetailSet; itemStockDetailsIterator.hasNext(); ) {
                ItemStockDetails itemStockDetails1 = itemStockDetailsIterator.next();
                if (itemStockDetails1 != null) {
                    if (itemStockDetails1.getId().equals(indentItemId)) {
                        itemStockDetailSet.remove();
                    }
                }
            }

        }
        indentRepository.save(indent);

    }

    public void deleteIndent(Long id) {
        indentRepository.deleteById(id);
    }

    /**
     * Method to delete IndentDetails by id
     *
     * @param id
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public Indent deleteIndentDetails(Long id) {
        MastroLogUtils.info(IndentService.class, "Going to delete IndentDetails by id : {}" + id);
        Indent indentDetails = new Indent();
        if (id != null) {
            indentDetails = getIndentById(id);
            indentDetails.setIndentDeleteStatus(1);
            indentRepository.save(indentDetails);
        }
        return indentDetails;

    }

}
