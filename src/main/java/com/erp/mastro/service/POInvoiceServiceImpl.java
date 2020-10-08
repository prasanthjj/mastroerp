package com.erp.mastro.service;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.POInvoice;
import com.erp.mastro.entities.PurchaseOrder;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.POInvoiceRequestModel;
import com.erp.mastro.repository.POInvoiceRepository;
import com.erp.mastro.repository.PurchaseOrderRepository;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.POInvoiceService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
/**
 * Include po invoice methods
 */
public class POInvoiceServiceImpl implements POInvoiceService {

    @Autowired
    private POInvoiceRepository poInvoiceRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void generatePOInvoice(POInvoiceRequestModel poInvoiceRequestModel) throws ModelNotFoundException {

        if (poInvoiceRequestModel == null) {
            throw new ModelNotFoundException("poInvoiceRequestModel is empty");
        } else {
            MastroLogUtils.info(POInvoiceService.class, "Going to generate poinvoice  {}" + poInvoiceRequestModel.toString());
            POInvoice poInvoice = new POInvoice();
            poInvoice.setBranch(branchService.getBranchById(poInvoiceRequestModel.getBranchId()));
            poInvoice.setPurchaseOrder(purchaseOrderService.getPurchaseOrderById(poInvoiceRequestModel.getPoId()));
            poInvoice.setTotalTaxableValue(poInvoiceRequestModel.getTotalTaxableValue());
            poInvoice.setSubTotal(poInvoiceRequestModel.getSubTotal());
            poInvoice.setTotalCess(poInvoiceRequestModel.getTotalCess());
            poInvoice.setTotalCgst(poInvoiceRequestModel.getTotalCgst());
            poInvoice.setTotalSgst(poInvoiceRequestModel.getTotalSgst());
            poInvoice.setTotalLoadingCharge(poInvoiceRequestModel.getTotalLoadingCharge());
            poInvoice.setGrandTotal(poInvoiceRequestModel.getGrandTotal());
            poInvoice.setRoundOff(poInvoiceRequestModel.getRoundValue());
            poInvoice.setTotalAmt(poInvoiceRequestModel.getTotalAmt());
            poInvoice.setGrandTotal(poInvoiceRequestModel.getGrandTotal());
            poInvoice.setPaymentMode(poInvoiceRequestModel.getPaymentMode());
            poInvoiceRepository.save(poInvoice);
            String currentBranchCode = poInvoice.getBranch().getBranchCode();
            if (currentBranchCode != null) {
                poInvoice.setPoInvoiceNo(MastroApplicationUtils.generateName(currentBranchCode, "PINV", poInvoice.getId()));
            }
            poInvoiceRepository.save(poInvoice);
            PurchaseOrder purchaseOrder = poInvoice.getPurchaseOrder();
            purchaseOrder.setStatus(Constants.STATUS_PO_INVOICED);
            purchaseOrderRepository.save(purchaseOrder);
            MastroLogUtils.info(POInvoiceService.class, "Generate invoice succesfully.");
        }
    }

    /**
     * Method to get po invoice
     *
     * @param id
     * @return poinvoice
     */
    public POInvoice getPOInvoiceyId(Long id) {
        MastroLogUtils.info(POInvoiceService.class, "Going to get invoice by id :{}" + id);
        return poInvoiceRepository.findById(id).get();
    }
}
