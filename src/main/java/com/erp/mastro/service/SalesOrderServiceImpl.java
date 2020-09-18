 package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesOrderRequestModel;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.repository.ProductRepository;
import com.erp.mastro.repository.SalesOrderRepository;
import com.erp.mastro.service.interfaces.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CalculateService calculateService;

    /**
     * method to get all salesorder
     *
     * @return
     */

    public List<SalesOrder> getAllSalesOrder() {
        List<SalesOrder> salesOrderList = new ArrayList<>();
        salesOrderRepository.findAll().forEach(salesOrder -> salesOrderList.add(salesOrder));
        return salesOrderList;
    }

    /**
     * Method to get salesorder by id
     *
     * @param id
     * @return
     */

    public SalesOrder getSalesorderById(Long id) {

        SalesOrder salesOrder = new SalesOrder();
        if (id != null) {
            MastroLogUtils.info(SalesOrderService.class, "Going to get SalesOrder By Id : {}" + id);
            salesOrder = salesOrderRepository.findById(id).get();
        }
        return salesOrder;
    }

    /**
     * Method to get all party
     *
     * @return
     */
    public List<Party> getAllParty() {
        List<Party> partyList = new ArrayList<Party>();
        partyRepository.findAll().forEach(party -> partyList.add(party));
        return partyList;
    }

    /**
     * Method to save sales order
     *
     * @param salesOrderRequestModel
     * @param party
     * @return
     * @throws ModelNotFoundException
     */

    public SalesOrder saveSalesOrder(SalesOrderRequestModel salesOrderRequestModel, Party party) throws ModelNotFoundException {

        SalesOrder salesOrder = new SalesOrder();
        if (salesOrderRequestModel == null) {
            throw new ModelNotFoundException("SalesOrderRequestModel is empty");
        } else {
            MastroLogUtils.info(SalesOrderService.class, "Going to save salesorder{}" + salesOrderRequestModel.getId());

            salesOrder.setParty(party);
            salesOrderRepository.save(salesOrder);
        }
        return salesOrder;
    }

    /**
     * Method to save SalesOrderProduct
     *
     * @param salesOrderRequestModel
     * @param salesOrder
     * @param product
     * @param quantity
     * @return
     * @throws ModelNotFoundException
     */
    @Transactional
    public SalesOrderProduct saveOrUpdateSalesOrderProduct(SalesOrderRequestModel salesOrderRequestModel, SalesOrder salesOrder, Product product, Double quantity) throws ModelNotFoundException {
        MastroLogUtils.info(SalesOrderService.class, "Going to save salesorderProduct : {}" + salesOrder.getId());
        salesOrder.setStatus("Draft");

        SalesOrder salesOrder1 = salesOrder;

        Set<ProductPartyRateRelation> productPartyRateRelationSet=product.getProductPartyRateRelations().stream()
                .filter(productPartyData -> (null !=productPartyData))
                .filter(productPartyData -> (salesOrder1.getParty().getId().equals(productPartyData.getParty().getId())))
                .collect(Collectors.toSet());

        Double partyProductRate= 0d;
        Double partyProductDiscount= 0d;
        SalesOrderProduct salesOrderProduct = new SalesOrderProduct();

        if(productPartyRateRelationSet.isEmpty()==false) {
            ProductPartyRateRelation productPartyRateRelation = productPartyRateRelationSet.stream().findFirst().get();
            partyProductRate = productPartyRateRelation.getPartyPriceList().getRate();
            partyProductDiscount = productPartyRateRelation.getPartyPriceList().getDiscount();


            Set<SalesOrderProduct> salesOrderProductSet = salesOrder1.getSalesOrderProductSet();
            salesOrderProduct.setProduct(product);
            salesOrderProduct.setQuantity(quantity);
            Double taxableValue = 0d;
            taxableValue = calculateService.calculateTaxableValueInSo(salesOrderProduct.getQuantity(), productPartyRateRelation);
            salesOrderProduct.setTaxableValue(taxableValue);
            Double finalTaxableValue = 0d;
            finalTaxableValue = calculateService.calculateFinalTaxableValueInSo(salesOrderProduct.getQuantity(), productPartyRateRelation);
            salesOrderProduct.setFinalTaxableValue(finalTaxableValue);
            System.out.println("Final taxable value:"+finalTaxableValue);

            Double singleCgstAmount = 0d;
            singleCgstAmount = calculateService.calculateSinglePriceCgstAmount(productPartyRateRelation,salesOrderProduct.getProduct().getHsn());
            salesOrderProduct.setSinglecgstAmount(singleCgstAmount);

            Double singlesgstAmount = 0d;
            singlesgstAmount = calculateService.calculateSinglePriceSgstAmount(productPartyRateRelation, salesOrderProduct.getProduct().getHsn());
            salesOrderProduct.setSinglesgstAmount(singlesgstAmount);


           Double totalCgstAmount = 0d;
            totalCgstAmount = calculateService.calculateTotalPriceCgstAmount(salesOrderProduct.getFinalTaxableValue(), salesOrderProduct.getProduct().getHsn());
            salesOrderProduct.setCgstAmount(totalCgstAmount);
            Double totalSgstAmount = 0d;
            totalSgstAmount = calculateService.calculateTotalPriceSgstAmount(salesOrderProduct.getFinalTaxableValue(), salesOrderProduct.getProduct().getHsn());
            salesOrderProduct.setSgstAmount(totalSgstAmount);

            Double totalPrice = 0d;
            totalPrice = calculateService.totalPriceForSO(salesOrderProduct.getFinalTaxableValue(), salesOrderProduct.getCgstAmount(), salesOrderProduct.getSgstAmount());
            salesOrderProduct.setTotalPrice(totalPrice);

            Double netPrice = 0d;
            netPrice = calculateService.totalNetPriceForSO(salesOrderProduct.getFinalTaxableValue(), salesOrderProduct.getSinglecgstAmount(), salesOrderProduct.getSinglesgstAmount(),productPartyRateRelation );
            salesOrderProduct.setNetPrice(netPrice);
             salesOrderProductSet.add(salesOrderProduct);
        }
        salesOrderRepository.save(salesOrder1);
        MastroLogUtils.info(SalesOrderService.class, "Save " + salesOrder1.getId() + " succesfully.");

        return salesOrderProduct;

    }

    /**
     * Method to generate Sales order
     *
     * @param salesOrderRequestModel
     * @param salesOrder
     * @param grandTotal
     * @throws ModelNotFoundException
     */

    @Transactional(rollbackOn = {Exception.class})
    public void generateSalesOrder(SalesOrderRequestModel salesOrderRequestModel, SalesOrder salesOrder, Double grandTotal) throws ModelNotFoundException {
        MastroLogUtils.info(SalesOrderService.class, "Going to createSalesOrder" + salesOrder.getId());
        SalesOrder salesOrder1 = salesOrder;
        if (salesOrderRequestModel == null) {
            throw new ModelNotFoundException("SalesOrderRequestModel model is empty");
        } else {
            salesOrder1.setSpecialInstructions(salesOrderRequestModel.getSpecialInstructions());
            salesOrder1.setRemarks(salesOrderRequestModel.getRemarks());
            salesOrder1.setGrandTotal(grandTotal);
            salesOrderRepository.save(salesOrder1);
            MastroLogUtils.info(SalesOrderService.class, "create sales order succesfully.");
        }

    }

}