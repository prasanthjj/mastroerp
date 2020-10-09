package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.SalesSlip;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;

import java.util.List;

public interface SalesSlipService {

    SalesSlip getSalesSlipById(Long id);

    SalesSlip createSalesSlip(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException;

    void grnUpdationOnSale(Long itemId, Long partyId, Double salequantity, Long productUOMSalesId, Double rate, Long grnItemIds, Long salesSlipId);

    void saveSalesSlipFullData(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException;

    List<SalesSlip> getAllSalesSlips();

    void generateSalesSlipInvoice(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException;

    void grnUpdationOnSaleSlipCut(Long grnItemsId, Long partyId, Long productSalesIds, Double rateValue, Double qtyEnter, Long salesUOMId, Long salesslipId);
}
