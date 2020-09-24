package com.erp.mastro.service;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;
import com.erp.mastro.repository.IndentItemPartyGroupRepository;
import com.erp.mastro.repository.IndentRepository;
import com.erp.mastro.repository.ItemStockDetailsRepository;
import com.erp.mastro.repository.PurchaseOrderRepository;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * All methods for purchase order
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private ItemStockDetailsRepository itemStockDetailsRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private IndentRepository indentRepository;

    @Autowired
    private PartyService partyService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private IndentItemPartyGroupRepository indentItemPartyGroupRepository;

    /**
     * Method to get a purchase order by if
     *
     * @param id
     * @return purchase order
     */
    public PurchaseOrder getPurchaseOrderById(Long id) {

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        if (id != null) {
            MastroLogUtils.info(PurchaseOrderService.class, "Going to getPurchaseOrderBy Id : {}" + id);
            purchaseOrder = purchaseOrderRepository.findById(id).get();
        }
        return purchaseOrder;
    }

    /**
     * Method to create indent item party group
     *
     * @param indentItemPartyGroupRequestModel
     * @return
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public ItemStockDetails IndentItemPartyGroup(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException {

        ItemStockDetails itemStockDetails = itemStockDetailsRepository.findById(indentItemPartyGroupRequestModel.getIndentItemId()).get();

        if (indentItemPartyGroupRequestModel == null) {
            throw new ModelNotFoundException("indentItemPartyGroupRequestModel is empty");
        } else {

            MastroLogUtils.info(PurchaseOrderService.class, "Going to save indentItemPartyGroup  {}" + indentItemPartyGroupRequestModel.toString());
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Indent indent = indentService.getIndentById(indentItemPartyGroupRequestModel.getIndentId());
            Party party = partyService.getPartyById(indentItemPartyGroupRequestModel.getPartyId());
            Set<IndentItemPartyGroup> indentItemPartyGroups = itemStockDetails.getIndentItemPartyGroups().stream()
                    .filter(groupData -> (null != groupData))
                    .filter(groupData -> (!groupData.isEnabled()))
                    .filter(groupData -> (groupData.getParty().getId().equals(party.getId())))
                    .collect(Collectors.toSet());
            IndentItemPartyGroup indentItemPartyGroup;
            if (indentItemPartyGroups.isEmpty() == false) {
                indentItemPartyGroup = indentItemPartyGroups.stream().findFirst().get();
            } else {
                indentItemPartyGroup = new IndentItemPartyGroup();
                indentItemPartyGroup.setBranch(currentBranch);
                indentItemPartyGroup.setParty(party);
                ProductPartyRateRelation productPartyRateRelations = party.getProductPartyRateRelations().stream()
                        .filter(productPartyRateRelation -> (null != productPartyRateRelation))
                        .filter(productPartyRateRelation -> (productPartyRateRelation.getProduct().getId().equals(itemStockDetails.getStock().getProduct().getId())))
                        .findFirst().get();
                if (productPartyRateRelations != null) {
                    indentItemPartyGroup.setRate(productPartyRateRelations.getPartyPriceList().getRate());
                }

                indentItemPartyGroup.setIndent(indent);
                indentItemPartyGroup.setItemStockDetails(itemStockDetails);
            }
            itemStockDetails.getIndentItemPartyGroups().add(indentItemPartyGroup);
            itemStockDetailsRepository.save(itemStockDetails);

            return itemStockDetails;
        }

    }

    /**
     * Method to save indent item party group data
     *
     * @param indentItemPartyGroupRequestModel
     * @return item stock details
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public ItemStockDetails IndentItemGroupDatas(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException {

        ItemStockDetails itemStockDetails = itemStockDetailsRepository.findById(indentItemPartyGroupRequestModel.getIndentItemId()).get();

        if (indentItemPartyGroupRequestModel == null) {
            throw new ModelNotFoundException("indentItemPartyGroupRequestModel model is empty");
        } else {

            MastroLogUtils.info(PurchaseOrderService.class, "Going to edit indentItemPartyGroup Items {}" + indentItemPartyGroupRequestModel.toString());
            Set<IndentItemPartyGroup> indentItemPartyGroups = saveOrUpdateIndentItemsPartyGroups(indentItemPartyGroupRequestModel, itemStockDetails);
            itemStockDetails.setIndentItemPartyGroups(indentItemPartyGroups);
            Double purchaseQuantity = 0d;
            for (IndentItemPartyGroup indentItemPartyGroup : itemStockDetails.getIndentItemPartyGroups()) {
                purchaseQuantity = purchaseQuantity + indentItemPartyGroup.getQuantity();
            }
            itemStockDetails.setPurchaseQuantity(purchaseQuantity);
            itemStockDetailsRepository.save(itemStockDetails);

            MastroLogUtils.info(PurchaseOrderService.class, "Save " + itemStockDetails.getId() + " succesfully.");

        }
        return itemStockDetails;

    }

    /**
     * Method to set indent item party group values
     *
     * @param indentItemPartyGroupRequestModel
     * @param itemStockDetails
     * @return
     * @throws ModelNotFoundException
     */
    private Set<IndentItemPartyGroup> saveOrUpdateIndentItemsPartyGroups(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, ItemStockDetails itemStockDetails) throws ModelNotFoundException {

        MastroLogUtils.info(PurchaseOrderService.class, "Going to edit indent items party group data {}" + indentItemPartyGroupRequestModel.toString());
        Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();

        if (indentItemPartyGroupRequestModel.getIndentItemPartyGroupRequestModels().isEmpty() == false) {

            for (IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels indentItemPartyGroupRequestModels : indentItemPartyGroupRequestModel.getIndentItemPartyGroupRequestModels()) {

                if (indentItemPartyGroupRequestModels.getId() != null) {
                    IndentItemPartyGroup indentItemPartyGroup = itemStockDetails.getIndentItemPartyGroups().stream()
                            .filter(indentItemPartyGrou -> (null != indentItemPartyGrou))
                            .filter(indentItemPartyGrou -> (indentItemPartyGrou.getId().equals(indentItemPartyGroupRequestModels.getId())))
                            .findFirst().get();

                    indentItemPartyGroup.setQuantity(indentItemPartyGroupRequestModels.getQuantity());
                    indentItemPartyGroups.add(indentItemPartyGroup);
                }
            }

        } else {
            throw new ModelNotFoundException("Indent item model is empty");
        }

        return indentItemPartyGroups;

    }

    /**
     * Method to create purchase order
     *
     * @param indentIds
     */
    @Transactional(rollbackOn = {Exception.class})
    public void generatePurchaseOrders(String indentIds) {

        MastroLogUtils.info(PurchaseOrderService.class, "Going to createPurchaseOrders" + indentIds);
        Long indentId = Long.parseLong(indentIds);
        Indent indent = indentService.getIndentById(indentId);
        Set<ItemStockDetails> indentIteamStockDetails = indent.getItemStockDetailsSet();
        Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();
        for (ItemStockDetails itemStockDetails : indentIteamStockDetails) {
            Set<IndentItemPartyGroup> indentItemPartyGroupsss = itemStockDetails.getIndentItemPartyGroups().stream()
                    .filter(indentItemPartyGroup -> (null != indentItemPartyGroup))
                    .filter(indentItemPartyGroup -> (!indentItemPartyGroup.isEnabled()))
                    .collect(Collectors.toSet());

            indentItemPartyGroups.addAll(indentItemPartyGroupsss);
        }
        if (indentItemPartyGroups.isEmpty() == false) {
            Set<Party> partySet = new HashSet();

            for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups) {
                partySet.add(indentItemPartyGroup.getParty());
            }
            for (Party party : partySet) {

                Set<IndentItemPartyGroup> indentItemPartyGroups1 = indentItemPartyGroups.stream()
                        .filter(indentItemPartyGroup -> (null != indentItemPartyGroup))
                        .filter(indentItemPartyGroup -> (indentItemPartyGroup.getParty().getId().equals(party.getId())))
                        .collect(Collectors.toSet());

                PurchaseOrder purchaseOrder = new PurchaseOrder();
                purchaseOrder.setParty(party);
                purchaseOrder.setIndent(indent);
                purchaseOrder.setStatus(Constants.STATUS_DRAFT);
                Set<ItemStockDetails> itemStockDetailsSet1 = new HashSet<>();
                for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups1) {
                    itemStockDetailsSet1.add(indentItemPartyGroup.getItemStockDetails());
                    indentItemPartyGroup.setEnabled(true);
                    indentItemPartyGroup.setPurchaseOrder(purchaseOrder);
                    indentItemPartyGroup.setHsnCode(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getHsnCode());
                    indentItemPartyGroup.setSgstRate(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getSgst());
                    indentItemPartyGroup.setCgstRate(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCgst());
                    if (indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCess() != null) {
                        indentItemPartyGroup.setCessRate(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCess());
                    } else {
                        indentItemPartyGroup.setCessRate(0.0);
                    }
                    indentItemPartyGroupRepository.save(indentItemPartyGroup);
                }
               // purchaseOrder.setItemStockDetailsSet(itemStockDetailsSet1);
                purchaseOrderRepository.save(purchaseOrder);
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                String currentBranchCode = currentBranch.getBranchCode();
                if (currentBranchCode != null) {
                    purchaseOrder.setPoNo(MastroApplicationUtils.generateName(currentBranchCode, "PO", purchaseOrder.getId()));
                }
                purchaseOrderRepository.save(purchaseOrder);
            }

        }

        int count = 0;
        for (ItemStockDetails itemStockDetailss : indentIteamStockDetails) {
            if (itemStockDetailss.getPurchaseQuantity() != null) {
                if (itemStockDetailss.getQuantityToIndent() > itemStockDetailss.getPurchaseQuantity()) {
                    count = count + 1;
                }
            } else {
                count = count + 1;
            }
        }
        if (count == 0) {
            if (indentItemPartyGroups.isEmpty() == false) {
                indent.setIndentStatus("CLOSED");
                indentRepository.save(indent);
            }
        }

        MastroLogUtils.info(PurchaseOrderService.class, "create purchase orders  succesfully.");

    }

    /**
     * Method to list all purchase orders
     *
     * @return purchase order list
     */
    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
        purchaseOrderRepository.findAll().forEach(po -> purchaseOrderList.add(po));
        return purchaseOrderList;
    }

    /**
     * Method to remove an indent item before save
     *
     * @param indentIteamId
     * @param indentItemGroupId
     */
    public void removeIndentItemGroup(Long indentIteamId, Long indentItemGroupId) {

        if (indentItemGroupId != null) {
            MastroLogUtils.info(PurchaseOrderService.class, "Going to remove indent item group {}" + indentItemGroupId);
            indentItemPartyGroupRepository.deleteById(indentItemGroupId);

        }

    }

    /**
     * Method to get an indent item stock by id
     *
     * @param id
     * @return the item
     */
    private ItemStockDetails getIndentItemStockById(Long id) {

        ItemStockDetails itemStockDetails = new ItemStockDetails();
        if (id != null) {
            MastroLogUtils.info(PurchaseOrderService.class, "Going to getIndentIteamBy Id : {}" + id);
            itemStockDetails = itemStockDetailsRepository.findById(id).get();
        }
        return itemStockDetails;
    }

    @Transactional(rollbackOn = {Exception.class})
    public ItemStockDetails IndentItemGroupDatasInEdit(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException {

        ItemStockDetails itemStockDetails = itemStockDetailsRepository.findById(indentItemPartyGroupRequestModel.getIndentItemId()).get();

        if (indentItemPartyGroupRequestModel == null) {
            throw new ModelNotFoundException("indentItemPartyGroupRequestModel model is empty");
        } else {

            MastroLogUtils.info(PurchaseOrderService.class, "Going to edit indentItemPartyGroup Items {}" + indentItemPartyGroupRequestModel.toString());
            Double purchaseQuantity = 0d;
            purchaseQuantity = itemStockDetails.getPurchaseQuantity();
            for (IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels indentItemPartyGroupRequestModels : indentItemPartyGroupRequestModel.getIndentItemPartyGroupRequestModels()) {
                if (indentItemPartyGroupRequestModels.getId() != null) {
                    IndentItemPartyGroup indentItemPartyGroup = itemStockDetails.getIndentItemPartyGroups().stream()
                            .filter(indentItemPartyGrou -> (null != indentItemPartyGrou))
                            .filter(indentItemPartyGrou -> (indentItemPartyGrou.getId().equals(indentItemPartyGroupRequestModels.getId())))
                            .findFirst().get();

                    purchaseQuantity = purchaseQuantity - indentItemPartyGroup.getQuantity();

                }
            }
            Set<IndentItemPartyGroup> indentItemPartyGroups = saveOrUpdateIndentItemsPartyGroupsEdit(indentItemPartyGroupRequestModel, itemStockDetails);
            for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups) {
                purchaseQuantity = purchaseQuantity + indentItemPartyGroup.getQuantity();
            }
            itemStockDetails.setPurchaseQuantity(purchaseQuantity);
            itemStockDetailsRepository.save(itemStockDetails);

            MastroLogUtils.info(PurchaseOrderService.class, "Save " + itemStockDetails.getId() + " succesfully.");

        }
        return itemStockDetails;

    }

    private Set<IndentItemPartyGroup> saveOrUpdateIndentItemsPartyGroupsEdit(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, ItemStockDetails itemStockDetails) throws ModelNotFoundException {

        MastroLogUtils.info(PurchaseOrderService.class, "Going to edit indent items party group data {}" + indentItemPartyGroupRequestModel.toString());
        Set<IndentItemPartyGroup> indentItemPartyGroups = new HashSet<>();

        if (indentItemPartyGroupRequestModel.getIndentItemPartyGroupRequestModels().isEmpty() == false) {

            for (IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels indentItemPartyGroupRequestModels : indentItemPartyGroupRequestModel.getIndentItemPartyGroupRequestModels()) {

                if (indentItemPartyGroupRequestModels.getId() != null) {
                    IndentItemPartyGroup indentItemPartyGroup = itemStockDetails.getIndentItemPartyGroups().stream()
                            .filter(indentItemPartyGrou -> (null != indentItemPartyGrou))
                            .filter(indentItemPartyGrou -> (indentItemPartyGrou.getId().equals(indentItemPartyGroupRequestModels.getId())))
                            .findFirst().get();

                    indentItemPartyGroup.setQuantity(indentItemPartyGroupRequestModels.getQuantity());
                    indentItemPartyGroupRepository.save(indentItemPartyGroup);
                    indentItemPartyGroups.add(indentItemPartyGroup);
                }
            }

        } else {
            throw new ModelNotFoundException("Indent item model is empty");
        }

        return indentItemPartyGroups;

    }

    @Transactional(rollbackOn = {Exception.class})
    public void editGeneratePurchaseOrders(String indentIds, String purchaseId) {

        MastroLogUtils.info(PurchaseOrderService.class, "Going to createPurchaseOrders" + indentIds);
        Long indentId = Long.parseLong(indentIds);
        Indent indent = indentService.getIndentById(indentId);
        Set<ItemStockDetails> indentIteamStockDetails = indent.getItemStockDetailsSet();

        int count = 0;
        for (ItemStockDetails itemStockDetailss : indentIteamStockDetails) {
            if (itemStockDetailss.getPurchaseQuantity() != null) {
                if (itemStockDetailss.getQuantityToIndent() > itemStockDetailss.getPurchaseQuantity()) {
                    count = count + 1;
                }
            } else {
                count = count + 1;
            }
        }
        if (count == 0) {
            indent.setIndentStatus("CLOSED");

        } else {
            indent.setIndentStatus("OPEN");
        }
        indentRepository.save(indent);
        PurchaseOrder purchaseOrder = getPurchaseOrderById(Long.parseLong(purchaseId));
        purchaseOrder.setStatus(Constants.STATUS_DRAFT);
        purchaseOrderRepository.save(purchaseOrder);
        MastroLogUtils.info(PurchaseOrderService.class, "create purchase orders  succesfully.");

    }

    /**
     * po discard method
     *
     * @param id
     */
    public void poDiscardChange(Long id) {

        MastroLogUtils.info(PurchaseOrderService.class, "Going to discard po items poId : {}" + id);
        PurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        Set<IndentItemPartyGroup> indentItemPartyGroups = purchaseOrder.getIndentItemPartyGroups();
        for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups) {
            ItemStockDetails itemStockDetails = indentItemPartyGroup.getItemStockDetails();
            Double itemPurchaseQty = itemStockDetails.getPurchaseQuantity() - indentItemPartyGroup.getQuantity();
            itemStockDetails.setPurchaseQuantity(itemPurchaseQty);
            itemStockDetailsRepository.save(itemStockDetails);
            indentItemPartyGroup.setItemStockDetails(null);
            indentItemPartyGroup.setPurchaseOrder(null);
            indentItemPartyGroup.setIndent(null);
            indentItemPartyGroupRepository.save(indentItemPartyGroup);
        }
        Indent indent = purchaseOrder.getIndent();
        Set<ItemStockDetails> indentIteamStockDetails = indent.getItemStockDetailsSet();
        int count = 0;
        for (ItemStockDetails itemStockDetailss : indentIteamStockDetails) {
            if (itemStockDetailss.getPurchaseQuantity() != null) {
                if (itemStockDetailss.getQuantityToIndent() > itemStockDetailss.getPurchaseQuantity()) {
                    count = count + 1;
                }
            } else {
                count = count + 1;
            }
        }
        if (count == 0) {
            indent.setIndentStatus("CLOSED");

        } else {
            indent.setIndentStatus("OPEN");
        }
        indentRepository.save(indent);

    }

}
