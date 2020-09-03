package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.SalesOrder;
import com.erp.mastro.entities.SalesOrderProduct;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesOrderRequestModel;

import java.util.List;

public interface SalesOrderService {

    List<SalesOrder> getAllSalesOrder();

    SalesOrder getSalesorderById(Long id);

    List<Party> getAllParty();

    SalesOrder saveSalesOrder(SalesOrderRequestModel salesOrderRequestModel, Party party) throws ModelNotFoundException;

    SalesOrderProduct saveOrUpdateSalesOrderProduct(SalesOrderRequestModel salesOrderRequestModel, SalesOrder salesOrder, Product product, Double quantity) throws ModelNotFoundException;
}
