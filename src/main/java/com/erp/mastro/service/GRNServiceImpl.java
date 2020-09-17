package com.erp.mastro.service;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.repository.*;
import com.erp.mastro.service.interfaces.GRNService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GRNServiceImpl implements GRNService {

    @Autowired
    private GRNRepository grnRepository;

    @Autowired
    private PartyService partyService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserController userController;

    @Autowired
    private IndentItemPartyGroupRepository indentItemPartyGroupRepository;

    @Autowired
    private CalculateService calculateService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private GRNItemRepository grnItemRepository;

    /**
     * Method to get all grns
     *
     * @return grn list
     */
    public List<GRN> getAllGRNs() {
        List<GRN> grnList = new ArrayList<GRN>();
        grnRepository.findAll().forEach(grn -> grnList.add(grn));
        return grnList;
    }

    /**
     * Method to create GRN
     *
     * @param grnRequestModel
     * @return grn
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public GRN createGRN(GRNRequestModel grnRequestModel) throws ModelNotFoundException {

        GRN grn = new GRN();
        if (grnRequestModel == null) {
            throw new ModelNotFoundException("GRN model is empty");
        } else {
            if (grnRequestModel.getId() == null) {
                MastroLogUtils.info(GRNRequestModel.class, "Going to create GRN :" + grnRequestModel.toString());
                grn.setParty(partyService.getPartyById(grnRequestModel.getSelectedParty()));
                grn.setReceivedAganist(grnRequestModel.getReceivedAganist());
                grn.setPurchaseOrder(purchaseOrderService.getPurchaseOrderById(grnRequestModel.getPoId()));
                grn.setReceivedThrough(grnRequestModel.getReceivedThrough());
                grn.setReceivedAs(grnRequestModel.getReceivedAs());
                grn.setStatus(Constants.STATUS_INITIAL);
                grn.setNumber(grnRequestModel.getPartyInvoiceNo());
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                grn.setBranch(currentBranch);
                String sDate1 = grnRequestModel.getSpartyInvoiceDate();
                if (!sDate1.equals("")) {
                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    grn.setDate(date1);
                }
                grnRepository.save(grn);
            }

        }
        return grn;
    }

    /**
     * Method to get grn
     *
     * @param id
     * @return grn
     */
    public GRN getGRNById(Long id) {
        GRN grn = new GRN();
        if (id != null) {
            MastroLogUtils.info(GRNService.class, "Going to getGRNById : " + id);
            grn = grnRepository.findById(id).get();
        }

        return grn;
    }

    /**
     * Method to save grn
     *
     * @param grnRequestModel
     * @param grnId
     * @return
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public GRN saveOrUpdateGRNItemDetails(GRNRequestModel grnRequestModel, Long grnId) throws ModelNotFoundException {

        GRN grn = getGRNById(grnId);

        if (grnRequestModel == null) {
            throw new ModelNotFoundException("AssetRequestModel model is empty");
        } else {
            MastroLogUtils.info(GRNService.class, "Going to Add grn items " + grnRequestModel.toString());
            grn.setRemarks(grnRequestModel.getRemarks());
            grn.setUser(userController.getCurrentUser());
            Set<GRNItems> grnItemsSet = saveOrUpdateGRNItems(grnRequestModel, grn);
            grn.setStatus(Constants.STATUS_DRAFT);
            grn.setGrnItems(grnItemsSet);
            String currentBranchCode = grn.getBranch().getBranchCode();
            if (currentBranchCode != null) {
                grn.setGrnNo(MastroApplicationUtils.generateName(currentBranchCode,"GRN",grn.getId()));
            }
            grnRepository.save(grn);

            int count = grnItemsSet.size();
            for (GRNItems grnItems : grnItemsSet) {
                if (grnItems.getPending() == 0) {
                    IndentItemPartyGroup indentItemPartyGroup = grnItems.getIndentItemPartyGroup();
                    indentItemPartyGroup.setGrnPendingStatus(1);
                    indentItemPartyGroupRepository.save(indentItemPartyGroup);
                    count = count - 1;
                }

            }
            if (count == 0) {
                PurchaseOrder purchaseOrder = grn.getPurchaseOrder();
                purchaseOrder.setStatus(Constants.STATUS_CLOSED);
                purchaseOrderRepository.save(purchaseOrder);
            } else {
                PurchaseOrder purchaseOrder = grn.getPurchaseOrder();
                purchaseOrder.setStatus(Constants.STATUS_PO_DELIVERYINPROGRESS);
                purchaseOrderRepository.save(purchaseOrder);
            }

        }
        return grn;
    }

    /**
     * method to save GRN items
     *
     * @param grnRequestModel
     * @param grn
     * @return grn items set
     * @throws ModelNotFoundException
     */
    private Set<GRNItems> saveOrUpdateGRNItems(GRNRequestModel grnRequestModel, GRN grn) throws ModelNotFoundException {

        MastroLogUtils.info(GRNService.class, "Going to save GRN items :" + grnRequestModel.toString());
        Set<GRNItems> grnItemsSet = new HashSet<>();

        if (grnRequestModel.getGrnpoItemsModels().isEmpty() == false) {

            grnRequestModel.getGrnpoItemsModels().parallelStream().forEach(x -> {
                GRNItems grnItems;
                if (!containsInList(x.getId(), grn.getGrnItems().stream().filter(grnData -> (null != grnData)).map(y -> y.getId()).collect(Collectors.toList()))) {
                    grnItems = new GRNItems();
                    grnItems.setAccepted(x.getAccepted());
                    grnItems.setReceived(x.getReceived());
                    grnItems.setShortage(x.getShortage());
                    grnItems.setRejected(x.getRejected());
                    grnItems.setQuantityDc(x.getQuantityDc());
                    IndentItemPartyGroup indentItemPartyGroup = indentItemPartyGroupRepository.findById(x.getItemPartyGroupId()).get();
                    grnItems.setIndentItemPartyGroup(indentItemPartyGroup);
                    ProductPartyRateRelation productPartyRateRelation = indentItemPartyGroup.getParty().getProductPartyRateRelations().stream()
                            .filter(productPartyRateRelation1 -> (null != productPartyRateRelation1))
                            .filter(productPartyRateRelation1 -> (productPartyRateRelation1.getProduct().getId().equals(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getId())))
                            .findFirst().get();
                    grnItems.setDiscount(productPartyRateRelation.getPartyPriceList().getDiscount());
                    grnItems.setCgstRate(productPartyRateRelation.getProduct().getHsn().getCgst());
                    grnItems.setSgstRate(productPartyRateRelation.getProduct().getHsn().getSgst());
                    if (productPartyRateRelation.getProduct().getHsn().getCess() != null) {
                        grnItems.setCessRate(productPartyRateRelation.getProduct().getHsn().getCess());
                    } else {
                        grnItems.setCessRate(0.0);
                    }
                    grnItems.setIgstRate(productPartyRateRelation.getProduct().getHsn().getIgst());
                    Double total = 0d;
                    Uom purchaseUOM = indentItemPartyGroup.getItemStockDetails().getPurchaseUOM();
                    ProductUOM productUOMPurchase = indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                            .filter(productUOMData -> (null != productUOMData))
                            .filter(productUOMData -> (productUOMData.getTransactionType().equals("Purchase")))
                            .filter(productUOMData -> (productUOMData.getUom().getId().equals(purchaseUOM.getId())))
                            .findFirst().get();
                    total = MastroApplicationUtils.calculateTotalPrice(indentItemPartyGroup.getRate(), x.getAccepted() * productUOMPurchase.getConvertionFactor(), productPartyRateRelation.getPartyPriceList().getDiscount());
                    grnItems.setTotalPrice(total);
                    grnItems.setIgstAmount(MastroApplicationUtils.calculateTax(total, productPartyRateRelation.getProduct().getHsn().getIgst()));
                    grnItems.setCgstAmount(MastroApplicationUtils.calculateTax(total, productPartyRateRelation.getProduct().getHsn().getCgst()));
                    grnItems.setSgstAmount(MastroApplicationUtils.calculateTax(total, productPartyRateRelation.getProduct().getHsn().getSgst()));
                    if (productPartyRateRelation.getProduct().getHsn().getCess() != null) {
                        grnItems.setCessAmount(MastroApplicationUtils.calculateTax(total, productPartyRateRelation.getProduct().getHsn().getCess()));
                    } else {
                        grnItems.setCessAmount(0d);
                    }
                    grnItems.setGrn(grn);
                    Set<GRN> grns = indentItemPartyGroup.getPurchaseOrder().getGrnSet().stream()
                            .filter(grnData -> (null != grnData))
                            .filter(grnData -> (!grnData.getStatus().equals(Constants.STATUS_DISCARD)))
                            .collect(Collectors.toSet());

                    if (grns.isEmpty() == false) {
                        Set<GRNItems> grnItemsSets = new HashSet<>();
                        for (GRN grnn : grns) {
                            if (grnn.getGrnItems().isEmpty() == false) {
                                GRNItems grnItem = grnn.getGrnItems().stream()
                                        .filter(grnItemData -> (null != grnItemData))
                                        .filter(grnItemData -> (grnItemData.getIndentItemPartyGroup().getId().equals(indentItemPartyGroup.getId())))
                                        .findFirst().get();

                                grnItemsSets.add(grnItem);
                            }
                        }
                        if (grnItemsSets.isEmpty() == false) {
                            Double totalAccepted = 0.0d;
                            for (GRNItems grnItems1s : grnItemsSets) {
                                totalAccepted = totalAccepted + grnItems1s.getAccepted();
                            }
                            grnItems.setPending(indentItemPartyGroup.getQuantity() - (totalAccepted + x.getAccepted()));
                        } else {
                            grnItems.setPending(indentItemPartyGroup.getQuantity() - (x.getAccepted()));
                        }
                    }
                    grnItemsSet.add(grnItems);
                }
            });

        } else {
            throw new ModelNotFoundException("grn item model is empty");
        }

        return grnItemsSet;

    }

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    /**
     * Method to updation in stock based on grn approve
     *
     * @param grn
     */
    @Transactional(rollbackOn = {Exception.class})
    public void stockUpdationBasedOnGRN(GRN grn) {

        MastroLogUtils.info(GRNService.class, "Going to Update Stock after GRN approval :" + grn.getId());
        Set<GRNItems> grnItemsSet = grn.getGrnItems();
        for (GRNItems grnItems : grnItemsSet) {
            Stock stock = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock();
            Uom purchaseUOM = grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM();
            ProductUOM productUOMPurchase = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                    .filter(productuomData -> (null != productuomData))
                    .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                    .filter(productuomData -> (productuomData.getUom().getId().equals(purchaseUOM.getId())))
                    .findFirst().get();
            stock.setCurrentStock(stock.getCurrentStock() + grnItems.getAccepted() * productUOMPurchase.getConvertionFactor());
            stockRepository.save(stock);
        }

    }

    /**
     * Method to get GRNITEM
     *
     * @param id
     * @return grnitem
     */
    public GRNItems getGRNItemById(Long id) {
        GRNItems grnItem = new GRNItems();
        if (id != null) {
            MastroLogUtils.info(GRNService.class, "Going to getGRNItemById : {}" + id);
            grnItem = grnItemRepository.findById(id).get();
        }

        return grnItem;
    }

}
