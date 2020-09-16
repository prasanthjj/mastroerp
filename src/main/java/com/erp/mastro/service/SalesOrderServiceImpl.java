package com.erp.mastro.service;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.SalesOrder;
import com.erp.mastro.entities.SalesOrderProduct;
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
        salesOrder.setStatus(Constants.STATUS_DRAFT);

        SalesOrder salesOrder1 = salesOrder;
        Set<SalesOrderProduct> salesOrderProductSet = salesOrder1.getSalesOrderProductSet();
        SalesOrderProduct salesOrderProduct = new SalesOrderProduct();
        salesOrderProduct.setProduct(product);
        salesOrderProduct.setQuantity(quantity);
        Double taxableValue = MastroApplicationUtils.calculateTaxableValueInSo(salesOrderProduct.getQuantity(), salesOrderProduct.getProduct());
        salesOrderProduct.setTaxableValue(taxableValue);
        salesOrderProduct.setCgstAmount(MastroApplicationUtils.calculateTax(salesOrderProduct.getTaxableValue(), salesOrderProduct.getProduct().getHsn().getCgst()));
        salesOrderProduct.setSgstAmount(MastroApplicationUtils.calculateTax(salesOrderProduct.getTaxableValue(), salesOrderProduct.getProduct().getHsn().getSgst()));

        Double totalPrice = MastroApplicationUtils.totalPriceForSO(salesOrderProduct.getTaxableValue(), salesOrderProduct.getCgstAmount(), salesOrderProduct.getSgstAmount());
        salesOrderProduct.setTotalPrice(totalPrice);

        salesOrderProductSet.add(salesOrderProduct);

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