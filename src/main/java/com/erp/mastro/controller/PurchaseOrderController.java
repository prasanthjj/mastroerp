package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;
import com.erp.mastro.repository.ItemStockDetailsRepository;
import com.erp.mastro.repository.PurchaseOrderRepository;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
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

    @Autowired
    private ItemStockDetailsRepository itemStockDetailsRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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
                    .filter(po -> (!po.getStatus().equals("Discard")))
                    .filter(po -> (po.getIndent().getBranch().getId().equals(currentBranch.getId())))
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
            model.addAttribute("indentItemId", itemStockDetails.getId());
            return "views/splitIndentItem";

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while get indent item split :{}" + indentItemId, e);
            throw e;
        }
    }

    /**
     * Method to create indent item party group
     *
     * @param indentItemPartyGroupRequestModel
     * @param request
     * @param model
     * @return the list
     */
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

    /**
     * Method to save indent item group data
     *
     * @param indentItemPartyGroupRequestModel
     * @param request
     * @param model
     * @return the result
     */
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

    /**
     * Method to create purchase order
     *
     * @param request
     * @param model
     * @return purchase order list
     */
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

    /**
     * Method to remove indent item group
     *
     * @param model
     * @param request
     * @param indentItemId
     * @param indentItemGroupId
     * @return the response
     */
    @PostMapping("/removeIndentItemGroup")
    @ResponseBody
    public GenericResponse removeIndentItemGroup(Model model, HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId, @RequestParam("indentItemGroupId") Long indentItemGroupId) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to remove indent item group" + indentItemGroupId);
        try {

            purchaseOrderService.removeIndentItemGroup(indentItemId, indentItemGroupId);
            return new GenericResponse(true, "delete indent item group details");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while deleting indent item group details :{}", e);

            throw e;
        }

    }

    /**
     * Method to get indent item view
     *
     * @param model
     * @param request
     * @param indentItemId
     * @return view
     */
    @GetMapping("/getIndentItemForView")
    @ResponseBody
    public GenericResponse getIndentItemForView(Model model, HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId) {

        try {

            ItemStockDetails itemStockDetails = itemStockDetailsRepository.findById(indentItemId).get();
            List<IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView> indentItemPartyGroupRequestModels = new ArrayList<>();
            int count = 1;

            for (IndentItemPartyGroup indentItemPartyGroup : itemStockDetails.getIndentItemPartyGroups()) {
                IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView indentItemPartyGroupRequestModelsView = new IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView();
                indentItemPartyGroupRequestModelsView.setId(count);
                indentItemPartyGroupRequestModelsView.setPartyname(indentItemPartyGroup.getParty().getPartyName());
                indentItemPartyGroupRequestModelsView.setQty(indentItemPartyGroup.getQuantity());
                indentItemPartyGroupRequestModelsView.setRate(indentItemPartyGroup.getRate());

                indentItemPartyGroupRequestModels.add(indentItemPartyGroupRequestModelsView);

                count++;
            }

            return new GenericResponse(true, "get booking details")
                    .setProperty("indentiteamgroup", indentItemPartyGroupRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while getting indent iteam group view:{}", e);
            return new GenericResponse(false, e.getMessage());

        }

    }

    /**
     * Method to get purchase order preview
     *
     * @param request
     * @param poId
     * @param model
     * @return preview
     */
    @RequestMapping(value = "/getPurchaseOrderPreview", method = RequestMethod.GET)
    public String getPurchaseOrderPreview(HttpServletRequest request, @RequestParam("poId") Long poId, Model model) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to get PurchaseOrderPreview :{}" + poId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            if (poId != null) {
                PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
                model.addAttribute("purchaseOrderDetails", purchaseOrder);
                Party party = purchaseOrder.getParty();
                ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                        .findFirst().get();
                model.addAttribute("partyContactDetails", contactDetails);

                List<IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView> indentItemPartyGroupRequestModels = new ArrayList<>();
                Set<IndentItemPartyGroup> indentItemPartyGroups = purchaseOrder.getIndentItemPartyGroups();
                Double subTotal = 0d;
                Double tax = 0d;
                for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups) {
                    IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView indentItemPartyGroupRequestModelsView = new IndentItemPartyGroupRequestModel.IndentIteamPartGroupsView();
                    indentItemPartyGroupRequestModelsView.setQty(indentItemPartyGroup.getQuantity());
                    indentItemPartyGroupRequestModelsView.setItemname(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getProductName());
                    indentItemPartyGroupRequestModelsView.setBaseuom(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getUom().getUOM());
                    indentItemPartyGroupRequestModelsView.setPurchaseuom(indentItemPartyGroup.getItemStockDetails().getPurchaseUOM().getUOM());
                    indentItemPartyGroupRequestModelsView.setRate(indentItemPartyGroup.getRate());
                    indentItemPartyGroupRequestModelsView.setHsnnoo(indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getHsnCode());
                    Double itemTotalAmount = 0d;
                    Uom purchaseUOM = indentItemPartyGroup.getItemStockDetails().getPurchaseUOM();
                    ProductUOM productUOMPurchase = indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                            .filter(productuomData -> (null != productuomData))
                            .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                            .filter(productuomData -> (productuomData.getUom().getId().equals(purchaseUOM.getId())))
                            .findFirst().get();

                    itemTotalAmount = indentItemPartyGroup.getQuantity() * productUOMPurchase.getConvertionFactor() * indentItemPartyGroup.getRate();
                    indentItemPartyGroupRequestModelsView.setTotal(itemTotalAmount);
                    indentItemPartyGroupRequestModels.add(indentItemPartyGroupRequestModelsView);
                    subTotal = subTotal + itemTotalAmount;
                    Double taxCalculationPercentage = 0d;
                    if (indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCess() != null) {
                        taxCalculationPercentage = indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCgst() + indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getSgst() + indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCess();
                    } else {
                        taxCalculationPercentage = indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCgst() + indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getSgst();
                    }
                    tax = tax + ((itemTotalAmount * taxCalculationPercentage) / 100);
                }
                model.addAttribute("indentItemPartyGroupData", indentItemPartyGroupRequestModels);
                model.addAttribute("subTotal", Math.round(subTotal * 100.0) / 100.0);
                model.addAttribute("tax", Math.round(tax * 100.0) / 100.0);
                Double finalTotal = subTotal + tax;
                model.addAttribute("finalTotal", Math.round(finalTotal * 100.0) / 100.0);
            }

            return "views/purchaseOrderPreview";

        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while getPurchaseOrderPreview :{}" + poId, e);
            throw e;
        }
    }

    /**
     * Method to approve po
     *
     * @param model
     * @param request
     * @param reason
     * @param poId
     * @return response
     */
    @PostMapping("/poApprove")
    @ResponseBody
    public GenericResponse poApprove(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("poids") Long poId) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to approve po" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus(Constants.STATUS_APPROVED);
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            return new GenericResponse(true, "approve po");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on approve po:{}", e);

            throw e;
        }

    }

    /**
     * Method to review po
     *
     * @param model
     * @param request
     * @param reason
     * @param poId
     * @return response
     */
    @PostMapping("/poReview")
    @ResponseBody
    public GenericResponse poReview(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("poids") Long poId) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to Review po" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus("Reviewed");
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            return new GenericResponse(true, "poReview");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on poReview :{}", e);

            throw e;
        }

    }

    /**
     * Method to discard po
     *
     * @param model
     * @param request
     * @param reason
     * @param poId
     * @return response
     */
    @PostMapping("/poDiscard")
    @ResponseBody
    public GenericResponse poDiscard(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("poids") Long poId) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to poDiscard po" + poId);
        try {

            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus("Discard");
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            purchaseOrderService.poDiscardChange(purchaseOrder.getId());
            return new GenericResponse(true, "poDiscard ");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on poDiscard :{}", e);

            throw e;
        }

    }

    @RequestMapping(value = "/getPurchaseOrderOnReview", method = RequestMethod.GET)
    public String getPurchaseOrderOnReview(HttpServletRequest request, @RequestParam("indentId") Long indentId, @RequestParam("poId") Long poId, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent :{}" + indentId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            if (indentId != null) {
                Indent indent = indentService.getIndentById(indentId);
                model.addAttribute("indentDetails", indent);
            }
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            model.addAttribute("purchaseOrderDetails", purchaseOrder);
            return "views/editPoOnReview";

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getPurchaseOrderViaIndent :{}" + indentId, e);
            throw e;
        }
    }

    @RequestMapping(value = "/splitIndentItemReviewEdit", method = RequestMethod.GET)
    public String getsplitIndentItemReviewEdit(HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId, @RequestParam("indentId") Long indentId, @RequestParam("poId") Long poId, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent item supplyers edit:{}" + indentItemId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("indentItemId", indentItemId);
            model.addAttribute("poId", poId);

            if (indentId != null) {
                Indent indent = indentService.getIndentById(indentId);
                model.addAttribute("indentDetails", indent);
            }
            ItemStockDetails itemStockDetails = indentService.getIndentById(indentId).getItemStockDetailsSet().stream()
                    .filter(indentItem -> (null != indentItem))
                    .filter(indentItem -> (indentItem.getId().equals(indentItemId)))
                    .findFirst().get();
            Set<IndentItemPartyGroup> indentItemPartyGroups = itemStockDetails.getIndentItemPartyGroups().stream()
                    .filter(indentItemGroup -> (null != indentItemGroup))
                    .filter(indentItemGroup -> (indentItemGroup.getPurchaseOrder().getId().equals(poId)))
                    .collect(Collectors.toSet());
            IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel = new IndentItemPartyGroupRequestModel();
            indentItemPartyGroupRequestModel.setIndentItemId(itemStockDetails.getId());
            List<IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels> indentItemPartyGroupRequestModelsSet = new ArrayList<IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels>();
            for (IndentItemPartyGroup indentItemPartyGroup : indentItemPartyGroups) {
                IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels indentItemPartyGroupRequestModels = new IndentItemPartyGroupRequestModel.IndentItemPartyGroupRequestModels();
                indentItemPartyGroupRequestModels.setId(indentItemPartyGroup.getId());
                indentItemPartyGroupRequestModels.setParty(indentItemPartyGroup.getParty());
                indentItemPartyGroupRequestModels.setRate(indentItemPartyGroup.getRate());
                indentItemPartyGroupRequestModels.setQuantity(indentItemPartyGroup.getQuantity());
                indentItemPartyGroupRequestModelsSet.add(indentItemPartyGroupRequestModels);

            }
            indentItemPartyGroupRequestModel.setIndentItemPartyGroupRequestModels(indentItemPartyGroupRequestModelsSet);

            model.addAttribute("itemStockDetails", itemStockDetails);
            model.addAttribute("indentItemPartyGroupForm", indentItemPartyGroupRequestModel);
            model.addAttribute("indentItemId", itemStockDetails.getId());
            return "views/splitIndentItemonReviewEdit";

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while get indent item split :{}" + indentItemId, e);
            throw e;
        }
    }

    @PostMapping("/saveIndentItemGroupDataOnEdit")
    public String saveIndentItemGroupDataOnEdit(@ModelAttribute("indentItemPartyGroupForm") @Valid IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to save additional  details in edit: {}" + indentItemPartyGroupRequestModel.toString());
        String poId = request.getParameter("poId");
        try {
            purchaseOrderService.IndentItemGroupDatasInEdit(indentItemPartyGroupRequestModel);
            return "redirect:/purchase/getPurchaseOrderOnReview?indentId=" + indentItemPartyGroupRequestModel.getIndentId() + "&poId=" + poId;
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "indentItemPartyGroupRequestModel empty", e);
            return "redirect:/purchase/getPurchaseOrderOnReview?indentId=" + indentItemPartyGroupRequestModel.getIndentId() + "&poId=" + poId;
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while save indent item group details in edit : {}", e);
            throw e;
        }

    }

    @PostMapping("/editPO")
    public String editPO(HttpServletRequest request, Model model) {
        String indentId = request.getParameter("purchaseIndentId");
        String purchaseId = request.getParameter("purchasesId");
        MastroLogUtils.info(PurchaseOrderController.class, "Going to edit purchase order for the indent: {}" + indentId);
        try {
            purchaseOrderService.editGeneratePurchaseOrders(indentId, purchaseId);
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while edit purchase orders : {}", e);
            throw e;
        }

    }
}
