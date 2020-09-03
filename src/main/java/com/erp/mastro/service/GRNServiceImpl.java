package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.repository.GRNRepository;
import com.erp.mastro.repository.IndentItemPartyGroupRepository;
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
                MastroLogUtils.info(GRNRequestModel.class, "Going to create grn{}" + grnRequestModel.toString());
                grn.setParty(partyService.getPartyById(grnRequestModel.getSelectedParty()));
                grn.setReceivedAganist(grnRequestModel.getReceivedAganist());
                grn.setPurchaseOrder(purchaseOrderService.getPurchaseOrderById(grnRequestModel.getPoId()));
                grn.setReceivedThrough(grnRequestModel.getReceivedThrough());
                grn.setReceivedAs(grnRequestModel.getReceivedAs());
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
            MastroLogUtils.info(GRNService.class, "Going to getGRNById : {}" + id);
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
            MastroLogUtils.info(GRNService.class, "Going to Add grn items {}" + grnRequestModel.toString());
            grn.setRemarks(grnRequestModel.getRemarks());
            grn.setUser(userController.getCurrentUser());
            Set<GRNItems> grnItemsSet = saveOrUpdateGRNItems(grnRequestModel, grn);
            grn.setStatus("Draft");
            grn.setGrnItems(grnItemsSet);

            grnRepository.save(grn);
        }
        return grn;
    }

    /**
     * method to save grn items
     *
     * @param grnRequestModel
     * @param grn
     * @return grn items set
     * @throws ModelNotFoundException
     */
    private Set<GRNItems> saveOrUpdateGRNItems(GRNRequestModel grnRequestModel, GRN grn) throws ModelNotFoundException {

        MastroLogUtils.info(GRNService.class, "Going to save grn items  {}" + grnRequestModel.toString());
        Set<GRNItems> grnItemsSet = new HashSet<>();

        if (grnRequestModel.getGrnpoItemsModels().isEmpty() == false) {

            grnRequestModel.getGrnpoItemsModels().parallelStream().forEach(x -> {
                GRNItems grnItems;
                if (!containsInList(x.getId(), grn.getGrnItems().stream().filter(grndata -> (null != grndata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                    grnItems = new GRNItems();
                    grnItems.setAccepted(x.getAccepted());
                    grnItems.setPending(x.getPending());
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
                    total = calculateService.calculateTotalPrice(indentItemPartyGroup.getRate(), x.getAccepted(), productPartyRateRelation.getPartyPriceList().getDiscount());
                    grnItems.setTotalPrice(total);
                    grnItems.setIgstAmount(calculateService.calculateTotalPriceIgstAmount(total, productPartyRateRelation.getProduct().getHsn()));
                    grnItems.setCgstAmount(calculateService.calculateTotalPriceCgstAmount(total, productPartyRateRelation.getProduct().getHsn()));
                    grnItems.setSgstAmount(calculateService.calculateTotalPriceSgstAmount(total, productPartyRateRelation.getProduct().getHsn()));
                    grnItems.setCessAmount(calculateService.calculateTotalPriceCessAmount(total, productPartyRateRelation.getProduct().getHsn()));
                    grnItems.setGrn(grn);
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

}
