package com.erp.mastro.service;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.repository.*;
import com.erp.mastro.service.interfaces.GRNService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.SalesSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SalesSlipServiceImpl implements SalesSlipService {

    @Autowired
    private SalesSlipRepository salesSlipRepository;

    @Autowired
    private PartyService partyService;

    @Autowired
    private UserController userController;

    @Autowired
    private ProductService productService;

    @Autowired
    private GRNService grnService;

    @Autowired
    private ProductUOMRepository productUOMRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private GRNItemRepository grnItemRepository;

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private SalesSlipInvoiceRepository salesSlipInvoiceRepository;

    @Autowired
    private StockLedgerRepository stockLedgerRepository;

    /**
     * Method to get all sales slip
     *
     * @return sales slip list
     */
    public List<SalesSlip> getAllSalesSlips() {
        MastroLogUtils.info(SalesSlipService.class, "Going to get all sales slip");
        List<SalesSlip> salesSlipList = new ArrayList<SalesSlip>();
        salesSlipRepository.findAll().forEach(salesSlip -> salesSlipList.add(salesSlip));
        return salesSlipList;
    }

    /**
     * Method to get all sales slip invoices
     *
     * @return invoice list
     */
    public List<SalesSlipInvoice> getAllSalesSlipsInvoice() {
        MastroLogUtils.info(SalesSlipService.class, "Going to get all sales slip invoice");
        List<SalesSlipInvoice> salesSlipInvoiceList = new ArrayList<SalesSlipInvoice>();
        salesSlipInvoiceRepository.findAll().forEach(salesSlipInvoice -> salesSlipInvoiceList.add(salesSlipInvoice));
        return salesSlipInvoiceList;
    }

    /**
     * Method to get sales slip by id
     *
     * @param id
     * @return
     */
    public SalesSlip getSalesSlipById(Long id) {
        SalesSlip salesSlip = new SalesSlip();
        if (id != null) {
            MastroLogUtils.info(SalesSlipService.class, "Going to salesslipById : {}" + id);
            salesSlip = salesSlipRepository.findById(id).get();
        }

        return salesSlip;
    }

    /**
     * Method to save sales slip basic details
     *
     * @param salesSlipRequestModel
     * @return salesslip
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public SalesSlip createSalesSlip(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException {

        SalesSlip salesSlip = new SalesSlip();
        if (salesSlipRequestModel == null) {
            throw new ModelNotFoundException("SalesSlip model is empty");
        } else {
            if (salesSlipRequestModel.getId() == null) {
                MastroLogUtils.info(SalesSlipService.class, "Going to create sales slip{}" + salesSlipRequestModel.toString());
                salesSlip.setParty(partyService.getPartyById(salesSlipRequestModel.getSelectedPartyInSalesSlip()));
                salesSlip.setTransportMode(salesSlipRequestModel.getTransportMode());
                salesSlip.setVehicleNo(salesSlipRequestModel.getVehicleNo());
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                salesSlip.setBranch(currentBranch);
                salesSlipRepository.save(salesSlip);
            }

        }
        return salesSlip;
    }

    /**
     * Method with grn updation on sale
     *
     * @param itemId
     * @param partyId
     * @param salequantity
     * @param productUOMSalesId
     * @param rate
     * @param grnItemIds
     * @param salesSlipId
     */
    @Transactional(rollbackOn = {Exception.class})
    public void grnUpdationOnSale(Long itemId, Long partyId, Double salequantity, Long productUOMSalesId, Double rate, Long grnItemIds, Long salesSlipId) {

        Party party = partyService.getPartyById(partyId);
        Product product = productService.getProductById(itemId);
        ProductUOM productSaleUOM = productUOMRepository.findById(productUOMSalesId).get();
        SalesSlip salesSlip = salesSlipRepository.findById(salesSlipId).get();
        Double salesQtyInSalesUOM = salequantity;

        GRNItems grnItems = grnService.getGRNItemById(grnItemIds);
        Uom purchaseUOM = grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM();
        ProductUOM productUOMPurchase = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                .filter(productuomData -> (null != productuomData))
                .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                .filter(productuomData -> (productuomData.getUom().getId().equals(purchaseUOM.getId())))
                .findFirst().get();
        Double grnItemQtyInBaseUOM = grnItems.getAccepted() * productUOMPurchase.getConvertionFactor();
        Double grnItemQtyInSalesUOM = grnItemQtyInBaseUOM / productSaleUOM.getConvertionFactor();
        Double grnItemQtyInSalesUOMs = Math.round(grnItemQtyInSalesUOM * 100.0) / 100.0;
        Stock stock = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock();
        if (salesQtyInSalesUOM > 0) {
            SalesSlipItems salesSlipItems = new SalesSlipItems();
            salesSlipItems.setProduct(product);
            salesSlipItems.setGrnItems(grnItems);
            salesSlipItems.setQuantity(salequantity);
            salesSlipItems.setRate(rate);
            salesSlipItems.setProductUOM(productSaleUOM);
            salesSlipItems.setCgstRate(product.getHsn().getCgst());
            salesSlipItems.setSgstRate(product.getHsn().getSgst());
            salesSlipItems.setIgstRate(product.getHsn().getIgst());
            salesSlipItems.setHsnCode(product.getHsn().getHsnCode());
            if (product.getHsn().getCess() != null) {
                salesSlipItems.setCessRate(product.getHsn().getCess());
            } else {
                salesSlipItems.setCessRate(0.0);
            }
            Double discount = 0.0d;
            if (!party.getIndustryType().getIndustryType().equals("Cash Customer")) {
                Set<ProductPartyRateRelation> productPartyRateRelationSet = product.getProductPartyRateRelations().stream()
                        .filter(productPartyData -> (null != productPartyData))
                        .filter(productPartyData -> (partyId.equals(productPartyData.getParty().getId())))
                        .collect(Collectors.toSet());
                if (!productPartyRateRelationSet.isEmpty()) {
                    discount = productPartyRateRelationSet.stream().findFirst().get().getPartyPriceList().getDiscount();
                }
            }
            salesSlipItems.setDiscountPercentage(discount);
            if (salesQtyInSalesUOM == grnItemQtyInSalesUOMs) {
                Double currentStock = stock.getCurrentStock() - (salesQtyInSalesUOM * (productSaleUOM.getConvertionFactor()));
                stock.setCurrentStock(currentStock);
                stockRepository.save(stock);
                Double acceptedQtyRemaining = 0.0d;
                acceptedQtyRemaining = 0.0d;
                grnItems.setAccepted(acceptedQtyRemaining);
                grnItemRepository.save(grnItems);
                salesSlipItems.setGrnQty(grnItemQtyInSalesUOMs);

                Double totalForRound = MastroApplicationUtils.calculateTotalPrice(rate, (grnItemQtyInSalesUOMs * productSaleUOM.getConvertionFactor()), discount);
                salesSlipItems.setTotalAmount(MastroApplicationUtils.roundTwoDecimals(totalForRound));
                salesSlipItems.setIgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getIgst()));
                salesSlipItems.setCgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCgst()));
                salesSlipItems.setSgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getSgst()));
                salesSlipItems.setFinalAmount(MastroApplicationUtils.roundTwoDecimals(salesSlipItems.getTotalAmount() + salesSlipItems.getCgstAmount() + salesSlipItems.getSgstAmount()));
                if (product.getHsn().getCess() != null) {
                    salesSlipItems.setCessAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCess()));
                } else {
                    salesSlipItems.setCessAmount(0d);
                }
                salesSlipItems.setNetPrice(MastroApplicationUtils.totalNetPriceForSalesSlip(rate, product.getHsn(), productSaleUOM.getConvertionFactor(), discount));

                StockLedger stockLedger = new StockLedger();
                stockLedger.setBaseUom(stock.getProduct().getUom());
                stockLedger.setProduct(stock.getProduct());
                stockLedger.setBasePrice(stock.getProduct().getBasePrice());
                stockLedger.setCreationDate(new Date());
                stockLedger.setIssuedStock(salesQtyInSalesUOM * (productSaleUOM.getConvertionFactor()));
                stockLedger.setBranch(stock.getBranch());
                stockLedgerRepository.save(stockLedger);
                Double averageConsumption = 0.0d;
                averageConsumption = MastroApplicationUtils.averageConsumption(stock.getProduct(), stock.getBranch());
                stock.setAverageConsumption(averageConsumption);
                stockRepository.save(stock);
            } else if (salesQtyInSalesUOM < grnItemQtyInSalesUOMs) {
                grnItemQtyInSalesUOMs = grnItemQtyInSalesUOMs - salesQtyInSalesUOM;
                Double currentStock = stock.getCurrentStock() - (salesQtyInSalesUOM * (productSaleUOM.getConvertionFactor()));
                stock.setCurrentStock(currentStock);
                stockRepository.save(stock);

                Double acceptedQtyRemaining = 0.0d;
                Double acceptedqtyInBaseuom = grnItemQtyInSalesUOMs * productSaleUOM.getConvertionFactor();
                acceptedQtyRemaining = acceptedqtyInBaseuom / (productUOMPurchase.getConvertionFactor());
                grnItems.setAccepted(Math.round(acceptedQtyRemaining * 100.0) / 100.0);
                grnItemRepository.save(grnItems);
                salesSlipItems.setGrnQty(salesQtyInSalesUOM);

                Double totalForRound = MastroApplicationUtils.calculateTotalPrice(rate, (salesQtyInSalesUOM * productSaleUOM.getConvertionFactor()), discount);
                salesSlipItems.setTotalAmount(MastroApplicationUtils.roundTwoDecimals(totalForRound));
                salesSlipItems.setIgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getIgst()));
                salesSlipItems.setCgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCgst()));
                salesSlipItems.setSgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getSgst()));
                salesSlipItems.setFinalAmount(MastroApplicationUtils.roundTwoDecimals(salesSlipItems.getTotalAmount() + salesSlipItems.getCgstAmount() + salesSlipItems.getSgstAmount()));
                if (product.getHsn().getCess() != null) {
                    salesSlipItems.setCessAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCess()));
                } else {
                    salesSlipItems.setCessAmount(0d);
                }
                salesSlipItems.setNetPrice(MastroApplicationUtils.totalNetPriceForSalesSlip(rate, product.getHsn(), productSaleUOM.getConvertionFactor(), discount));

                StockLedger stockLedger = new StockLedger();
                stockLedger.setBaseUom(stock.getProduct().getUom());
                stockLedger.setProduct(stock.getProduct());
                stockLedger.setBasePrice(stock.getProduct().getBasePrice());
                stockLedger.setCreationDate(new Date());
                stockLedger.setIssuedStock(salesQtyInSalesUOM * (productSaleUOM.getConvertionFactor()));
                stockLedger.setBranch(stock.getBranch());
                stockLedgerRepository.save(stockLedger);

                Double averageConsumption = 0.0d;
                averageConsumption = MastroApplicationUtils.averageConsumption(stock.getProduct(), stock.getBranch());
                stock.setAverageConsumption(averageConsumption);
                stockRepository.save(stock);
            } else {
                salesQtyInSalesUOM = salesQtyInSalesUOM - grnItemQtyInSalesUOMs;
                Double currentStock = stock.getCurrentStock() - grnItemQtyInBaseUOM;
                stock.setCurrentStock(currentStock);
                stockRepository.save(stock);
                Double acceptedQtyRemaining = 0.0d;
                acceptedQtyRemaining = 0.0d;
                grnItems.setAccepted(acceptedQtyRemaining);
                grnItemRepository.save(grnItems);
                salesSlipItems.setGrnQty(grnItemQtyInSalesUOMs);

                Double totalForRound = MastroApplicationUtils.calculateTotalPrice(rate, (grnItemQtyInSalesUOMs * productSaleUOM.getConvertionFactor()), discount);
                salesSlipItems.setTotalAmount(MastroApplicationUtils.roundTwoDecimals(totalForRound));
                salesSlipItems.setIgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getIgst()));
                salesSlipItems.setCgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCgst()));
                salesSlipItems.setSgstAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getSgst()));
                salesSlipItems.setFinalAmount(MastroApplicationUtils.roundTwoDecimals(salesSlipItems.getTotalAmount() + salesSlipItems.getCgstAmount() + salesSlipItems.getSgstAmount()));
                if (product.getHsn().getCess() != null) {
                    salesSlipItems.setCessAmount(MastroApplicationUtils.calculateTax(salesSlipItems.getTotalAmount(), product.getHsn().getCess()));
                } else {
                    salesSlipItems.setCessAmount(0d);
                }
                salesSlipItems.setNetPrice(MastroApplicationUtils.totalNetPriceForSalesSlip(rate, product.getHsn(), productSaleUOM.getConvertionFactor(), discount));

                StockLedger stockLedger = new StockLedger();
                stockLedger.setBaseUom(stock.getProduct().getUom());
                stockLedger.setProduct(stock.getProduct());
                stockLedger.setCreationDate(new Date());
                stockLedger.setBasePrice(stock.getProduct().getBasePrice());
                stockLedger.setIssuedStock(grnItemQtyInBaseUOM);
                stockLedger.setBranch(stock.getBranch());
                stockLedgerRepository.save(stockLedger);

                Double averageConsumption = 0.0d;
                averageConsumption = MastroApplicationUtils.averageConsumption(stock.getProduct(), stock.getBranch());
                stock.setAverageConsumption(averageConsumption);
                stockRepository.save(stock);
            }
            Set<SalesSlipItems> salesSlipItemsSet = salesSlip.getSalesSlipItemsSet();
            salesSlipItemsSet.add(salesSlipItems);
            salesSlip.setSalesSlipItemsSet(salesSlipItemsSet);
            salesSlipRepository.save(salesSlip);
        }

    }

    /**
     * Method to save sales slip full data
     *
     * @param salesSlipRequestModel
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public void saveSalesSlipFullData(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException {

        if (salesSlipRequestModel == null) {
            throw new ModelNotFoundException("SalesSlip model is empty");
        } else {
            if (salesSlipRequestModel.getId() != null) {
                SalesSlip salesSlip = getSalesSlipById(salesSlipRequestModel.getId());
                MastroLogUtils.info(SalesSlipService.class, "Going to save sales slip full data{}" + salesSlipRequestModel.toString());
                salesSlip.setRemarks(salesSlipRequestModel.getRemarks());
                salesSlip.setSpecificInst(salesSlipRequestModel.getSpecificInst());
                salesSlip.setStatus(Constants.STATUS_DRAFT);
                salesSlip.setTotalAmt(salesSlipRequestModel.getTotalAmt());
                salesSlip.setRoundOff(salesSlipRequestModel.getRoundOff());
                salesSlip.setGrandTotal(salesSlipRequestModel.getGrandTotal());
                salesSlip.setSubTotal(salesSlipRequestModel.getSubTotal());
                salesSlip.setTotalCess(salesSlipRequestModel.getTotalCess());
                salesSlip.setTotalSgst(salesSlipRequestModel.getTotalSgst());
                salesSlip.setTotalCgst(salesSlipRequestModel.getTotalCgst());
                salesSlip.setTotalLoadingCharge(salesSlipRequestModel.getTotalLoadingCharge());
                salesSlip.setTotalTaxableValue(salesSlipRequestModel.getTotalTaxableValue());
                salesSlip.setLoadingChargeSum(salesSlipRequestModel.getLoadingChargeSum());
                salesSlip.setLoadingChargeCgst(salesSlipRequestModel.getLoadingChargeCgst());
                salesSlip.setLoadingChargesgst(salesSlipRequestModel.getLoadingChargesgst());
                String currentBranchCode = salesSlip.getBranch().getBranchCode();
                if (currentBranchCode != null) {
                    salesSlip.setSalesSlipNo(MastroApplicationUtils.generateName(currentBranchCode, "SS", salesSlip.getId()));
                }
                salesSlipRepository.save(salesSlip);
            }

        }

    }

    /**
     * Method to generate sales slip invoice
     *
     * @param salesSlipRequestModel
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public void generateSalesSlipInvoice(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException {

        if (salesSlipRequestModel == null) {
            throw new ModelNotFoundException("salesSlipRequestModel is empty");
        } else {
            MastroLogUtils.info(SalesSlipService.class, "Going to generate sales slipinvoice " + salesSlipRequestModel.toString());
            SalesSlipInvoice salesSlipInvoice = new SalesSlipInvoice();
            SalesSlip salesSlip = getSalesSlipById(salesSlipRequestModel.getId());
            salesSlipInvoice.setBranch(salesSlip.getBranch());
            salesSlipInvoice.setSalesSlip(salesSlip);
            salesSlipInvoice.setTotalTaxableValue(salesSlipRequestModel.getTotalTaxableValue());
            salesSlipInvoice.setSubTotal(salesSlipRequestModel.getSubTotal());
            salesSlipInvoice.setTotalCess(salesSlipRequestModel.getTotalCess());
            salesSlipInvoice.setTotalCgst(salesSlipRequestModel.getTotalCgst());
            salesSlipInvoice.setTotalSgst(salesSlipRequestModel.getTotalSgst());
            salesSlipInvoice.setTotalLoadingCharge(salesSlipRequestModel.getTotalLoadingCharge());
            salesSlipInvoice.setGrandTotal(salesSlipRequestModel.getGrandTotal());
            salesSlipInvoice.setRoundOff(salesSlipRequestModel.getRoundOff());
            salesSlipInvoice.setTotalAmt(salesSlipRequestModel.getTotalAmt());
            salesSlipInvoice.setGrandTotal(salesSlipRequestModel.getGrandTotal());
            salesSlipInvoice.setPaymentMode(salesSlipRequestModel.getPaymentMode());
            salesSlipInvoiceRepository.save(salesSlipInvoice);
            String currentBranchCode = salesSlipInvoice.getBranch().getBranchCode();
            if (currentBranchCode != null) {
                salesSlipInvoice.setSalesSlipInvoiceNo(MastroApplicationUtils.generateName(currentBranchCode, "SINV", salesSlipInvoice.getId()));
            }
            salesSlipInvoiceRepository.save(salesSlipInvoice);
            salesSlip.setStatus(Constants.STATUS_SALESSLIP);
            salesSlipRepository.save(salesSlip);
            MastroLogUtils.info(SalesSlipService.class, "Generate invoice succesfully.");
        }
    }

}
