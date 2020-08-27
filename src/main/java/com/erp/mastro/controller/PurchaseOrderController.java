package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller with all purchase order methods
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private UserController userController;

    @Autowired
    private IndentService indentService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * method to get purchase order list
     *
     * @param model
     * @return list
     */
    @GetMapping("/getPurchaseOrderList")
    public String getIndentList(Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent list: {}");
        try {
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            List<PurchaseOrder> purchaseList = purchaseOrderService.getAllPurchaseOrders().stream()
                    .filter(po -> (null != po))
                    .sorted(Comparator.comparing(
                            PurchaseOrder::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("purchaseList", purchaseList);
            List<Indent> indentSet = currentBranch.getIndentSet().stream()
                    .filter(indentData -> (null != indentData))
                    .filter(indentData -> (1 != indentData.getIndentDeleteStatus()))
                    .filter(indentItem -> (indentItem.getIndentStatus().equals("OPEN")))
                    .sorted(Comparator.comparing(
                            Indent::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("indentSet", indentSet);

            return "views/purchaseOrder";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting indent list: {}", e);
            throw e;
        }

    }

    /**
     * Method to get indent details
     *
     * @param request
     * @param indentId
     * @param model
     * @return indent page
     */
    @RequestMapping(value = "/getPurchaseOrderViaIndent", method = RequestMethod.GET)
    public String getPurchaseOrderViaIndent(HttpServletRequest request, @RequestParam("indentId") Long indentId, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent :{}" + indentId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            if (indentId != null) {
                Indent indent = indentService.getIndentById(indentId);
                model.addAttribute("indentDetails", indent);
            }
            return "views/addPoViaIndent";

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getPurchaseOrderViaIndent :{}" + indentId, e);
            throw e;
        }
    }

    /**
     * Method to get party assosiation to indent iteam
     *
     * @param request
     * @param indentItemId
     * @param indentId
     * @param model
     * @return the result list
     */
    @RequestMapping(value = "/splitIndentItem", method = RequestMethod.GET)
    public String getsplitIndentItem(HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId, @RequestParam("indentId") Long indentId, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent item supplyers:{}" + indentItemId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("indentItemId", indentItemId);

            if (indentId != null) {
                Indent indent = indentService.getIndentById(indentId);
                model.addAttribute("indentDetails", indent);
            }
            ItemStockDetails itemStockDetails = indentService.getIndentById(indentId).getItemStockDetailsSet().stream()
                    .filter(indentItem -> (null != indentItem))
                    .filter(indentItem -> (indentItem.getId().equals(indentItemId)))
                    .findFirst().get();
            Set<ProductPartyRateRelation> productPartyRateRelationSet = itemStockDetails.getStock().getProduct().getProductPartyRateRelations();
            Set<Party> partySet = new HashSet<>();
            for (ProductPartyRateRelation productPartyRateRelation : productPartyRateRelationSet) {
                Party party = productPartyRateRelation.getParty();
                if (party.getPartyType().equals("Supplier")) {
                    partySet.add(party);
                }
            }
            model.addAttribute("supplierList", partySet);
            model.addAttribute("itemStockDetails", itemStockDetails);
            model.addAttribute("indentItemPartyGroupForm", new IndentItemPartyGroupRequestModel());
            // model.addAttribute("indentItemPartyGroupForm", new IndentItemPartyGroupRequestModel(itemStockDetails));
            model.addAttribute("indentItemId", itemStockDetails.getId());
            return "views/splitIndentItem";

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while get indent item split :{}" + indentItemId, e);
            throw e;
        }
    }

    @PostMapping("/createIndentItemPartyGroup")
    public String createIndent(@ModelAttribute("indentItemPartyGroupForm") @Valid IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to create IndentItemPartyGroup : {}" + indentItemPartyGroupRequestModel.toString());
        try {
            ItemStockDetails itemStockDetails = purchaseOrderService.IndentItemPartyGroup(indentItemPartyGroupRequestModel);
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("itemStockDetails", itemStockDetails);
            model.addAttribute("indentItemPartyGroupForm", new IndentItemPartyGroupRequestModel(itemStockDetails));

            Indent indent = indentService.getIndentById(indentItemPartyGroupRequestModel.getIndentId());
            model.addAttribute("indentDetails", indent);
            model.addAttribute("indentItemId", itemStockDetails.getId());
            Set<ProductPartyRateRelation> productPartyRateRelationSet = itemStockDetails.getStock().getProduct().getProductPartyRateRelations();
            Set<Party> partySet = new HashSet<>();
            for (ProductPartyRateRelation productPartyRateRelation : productPartyRateRelationSet) {
                Party party = productPartyRateRelation.getParty();
                if (party.getPartyType().equals("Supplier")) {
                    partySet.add(party);
                }
            }
            model.addAttribute("supplierList", partySet);
            return "views/splitIndentItem";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "IndentItemPartyGroupRequestModel empty", e);
            return "views/splitIndentItem";
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/saveIndentItemGroupData")
    public String saveIndentItemGroupData(@ModelAttribute("indentItemPartyGroupForm") @Valid IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to save additional  details: {}" + indentItemPartyGroupRequestModel.toString());
        try {
            purchaseOrderService.IndentItemGroupDatas(indentItemPartyGroupRequestModel);
            return "redirect:/purchase/getPurchaseOrderViaIndent?indentId=" + indentItemPartyGroupRequestModel.getIndentId();
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "indentItemPartyGroupRequestModel empty", e);
            return "redirect:/purchase/getPurchaseOrderViaIndent?indentId=" + indentItemPartyGroupRequestModel.getIndentId();
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while save indent item group details : {}", e);
            throw e;
        }

    }

    @PostMapping("/createPO")
    public String createPO(HttpServletRequest request, Model model) {
        String indentId = request.getParameter("purchaseIndentId");
        MastroLogUtils.info(PurchaseOrderController.class, "Going to create purchase order for the indent: {}" + indentId);
        try {
            purchaseOrderService.generatePurchaseOrders(indentId);
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while creating purchase orders : {}", e);
            throw e;
        }

    }
}
