package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
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

    @Transactional(rollbackOn = {Exception.class})
    public ItemStockDetails IndentItemPartyGroup(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException {

        ItemStockDetails itemStockDetails = itemStockDetailsRepository.findById(indentItemPartyGroupRequestModel.getIndentItemId()).get();

        if (indentItemPartyGroupRequestModel == null) {
            throw new ModelNotFoundException("indentItemPartyGroupRequestModel is empty");
        } else {

            MastroLogUtils.info(PurchaseOrderService.class, "Going to save indentItemPartyGroup  {}" + indentItemPartyGroupRequestModel.toString());
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            IndentItemPartyGroup indentItemPartyGroup = new IndentItemPartyGroup();
            indentItemPartyGroup.setBranch(currentBranch);
            Party party = partyService.getPartyById(indentItemPartyGroupRequestModel.getPartyId());
            indentItemPartyGroup.setParty(party);
            ProductPartyRateRelation productPartyRateRelations = party.getProductPartyRateRelations().stream()
                    .filter(productPartyRateRelation -> (null != productPartyRateRelation))
                    .filter(productPartyRateRelation -> (productPartyRateRelation.getProduct().getId().equals(itemStockDetails.getStock().getProduct().getId())))
                    .findFirst().get();
            if (productPartyRateRelations != null) {
                indentItemPartyGroup.setRate(productPartyRateRelations.getPartyPriceList().getRate());
            }
            Indent indent = indentService.getIndentById(indentItemPartyGroupRequestModel.getIndentId());
            indentItemPartyGroup.setIndent(indent);
            indentItemPartyGroup.setItemStockDetails(itemStockDetails);
            itemStockDetails.getIndentItemPartyGroups().add(indentItemPartyGroup);

            itemStockDetailsRepository.save(itemStockDetails);

            return itemStockDetails;
        }

    }

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
                purchaseOrder.setStatus("Draft");
                Set<ItemStockDetails> itemStockDetailsSet1 = new HashSet<>();
                for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups1) {
                    itemStockDetailsSet1.add(indentItemPartyGroup.getItemStockDetails());
                    indentItemPartyGroup.setEnabled(true);
                    indentItemPartyGroupRepository.save(indentItemPartyGroup);
                }
                purchaseOrder.setItemStockDetailsSet(itemStockDetailsSet1);
                purchaseOrderRepository.save(purchaseOrder);
            }

        }

        int count = 0;
        for (ItemStockDetails itemStockDetailss : indentIteamStockDetails) {
            if (itemStockDetailss.getPurchaseQuantity() != null) {
                if (itemStockDetailss.getQuantityToIndent() > itemStockDetailss.getPurchaseQuantity()) {
                    count = count + 1;
                }
            }
        }
        if (count == 0) {
            indent.setIndentStatus("CLOSED");
            indentRepository.save(indent);
        }

        MastroLogUtils.info(PurchaseOrderService.class, "create purchase orders  succesfully.");

    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrderList = new ArrayList<PurchaseOrder>();
        purchaseOrderRepository.findAll().forEach(po -> purchaseOrderList.add(po));
        return purchaseOrderList;
    }

    public void removeIndentItemGroup(Long indentIteamId, Long indentItemGroupId) {

        if (indentItemGroupId != null) {
            MastroLogUtils.info(PurchaseOrderService.class, "Going to remove indent item group {}" + indentItemGroupId);
            indentItemPartyGroupRepository.deleteById(indentItemGroupId);

        }

    }

    private ItemStockDetails getIndentItemStockById(Long id) {

        ItemStockDetails itemStockDetails = new ItemStockDetails();
        if (id != null) {
            MastroLogUtils.info(PurchaseOrderService.class, "Going to getIndentIteamBy Id : {}" + id);
            itemStockDetails = itemStockDetailsRepository.findById(id).get();
        }
        return itemStockDetails;
    }
}
