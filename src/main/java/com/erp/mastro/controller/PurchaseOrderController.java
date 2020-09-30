package com.erp.mastro.controller;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;
import com.erp.mastro.repository.GRNRepository;
import com.erp.mastro.repository.IndentItemPartyGroupRepository;
import com.erp.mastro.repository.ItemStockDetailsRepository;
import com.erp.mastro.repository.PurchaseOrderRepository;
import com.erp.mastro.service.interfaces.GRNService;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

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

    @Autowired
    private GRNService grnService;

    @Autowired
    private IndentItemPartyGroupRepository indentItemPartyGroupRepository;

    @Autowired
    private GRNRepository grnRepository;

    /**
     * method to get purchase order list
     *
     * @param model
     * @return list
     */
    @GetMapping("/getPurchaseOrderList")
    public String getIndentList(Model model) {
        logger.info("Going to get indent list: ");
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
            logger.error("Error occured while getting indent list: ", e);
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
        logger.info("Going to get indent :" + indentId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            if (indentId != null) {
                Indent indent = indentService.getIndentById(indentId);
                model.addAttribute("indentDetails", indent);
            }
            return "views/addPoViaIndent";

        } catch (Exception e) {
            logger.error("Error occured while getPurchaseOrderViaIndent :" + indentId, e);
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
        logger.info("Going to get indent item supplyers:" + indentItemId);
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
            Double qtyRequired = 0.0d;
            if (itemStockDetails.getPurchaseQuantity() != null) {
                qtyRequired = itemStockDetails.getQuantityToIndent() - itemStockDetails.getPurchaseQuantity();
            } else {
                qtyRequired = itemStockDetails.getQuantityToIndent();
            }
            model.addAttribute("qtyRequired", qtyRequired);
            return "views/splitIndentItem";

        } catch (Exception e) {
            logger.error("Error occured while get indent item split :" + indentItemId, e);
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
        logger.info("Going to create IndentItemPartyGroup : " + indentItemPartyGroupRequestModel.toString());
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
            Double qtyRequired = 0.0d;
            if (itemStockDetails.getPurchaseQuantity() != null) {
                qtyRequired = itemStockDetails.getQuantityToIndent() - itemStockDetails.getPurchaseQuantity();
            } else {
                qtyRequired = itemStockDetails.getQuantityToIndent();
            }
            model.addAttribute("qtyRequired", qtyRequired);
            return "views/splitIndentItem";
        } catch (ModelNotFoundException e) {
            logger.error("IndentItemPartyGroupRequestModel empty", e, this);
            return "views/splitIndentItem";
        } catch (Exception e) {
            logger.error(e.getMessage());
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
        logger.info("Going to save additional  details: " + indentItemPartyGroupRequestModel.toString());
        try {
            purchaseOrderService.IndentItemGroupDatas(indentItemPartyGroupRequestModel);
            return "redirect:/purchase/getPurchaseOrderViaIndent?indentId=" + indentItemPartyGroupRequestModel.getIndentId();
        } catch (ModelNotFoundException e) {
            logger.error("indentItemPartyGroupRequestModel empty", e, this);
            return "redirect:/purchase/getPurchaseOrderViaIndent?indentId=" + indentItemPartyGroupRequestModel.getIndentId();
        } catch (Exception e) {
            logger.error("Error occured while save indent item group details : ", e);
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
        logger.info("Going to create purchase order for the indent:" + indentId);
        try {
            purchaseOrderService.generatePurchaseOrders(indentId);
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            logger.error("Error occured while creating purchase orders :", e);
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
        logger.info("Going to remove indent item group" + indentItemGroupId);
        try {

            purchaseOrderService.removeIndentItemGroup(indentItemId, indentItemGroupId);
            return new GenericResponse(true, "delete indent item group details");

        } catch (Exception e) {
            logger.error("Error Occured while deleting indent item group details :", e, this);

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
            logger.error("Error Occured while getting indent iteam group view:", e, this);
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
        logger.info("Going to get PurchaseOrderPreview :" + poId);
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
                    indentItemPartyGroupRequestModelsView.setHsnnoo(indentItemPartyGroup.getHsnCode());
                    Double itemTotalAmount = 0d;
                    Uom purchaseUOM = indentItemPartyGroup.getItemStockDetails().getPurchaseUOM();
                    ProductUOM productUOMPurchase = indentItemPartyGroup.getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                            .filter(productuomData -> (null != productuomData))
                            .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                            .filter(productuomData -> (productuomData.getUom().getId().equals(purchaseUOM.getId())))
                            .findFirst().get();

                    itemTotalAmount = indentItemPartyGroup.getQuantity() * productUOMPurchase.getConvertionFactor() * indentItemPartyGroup.getRate();
                    indentItemPartyGroupRequestModelsView.setTotal(MastroApplicationUtils.roundTwoDecimals(itemTotalAmount));
                    indentItemPartyGroupRequestModels.add(indentItemPartyGroupRequestModelsView);
                    subTotal = subTotal + itemTotalAmount;
                    Double taxCalculationPercentage = 0d;
                    taxCalculationPercentage = indentItemPartyGroup.getCgstRate() + indentItemPartyGroup.getSgstRate() + indentItemPartyGroup.getCessRate();
                    tax = tax + ((itemTotalAmount * taxCalculationPercentage) / 100);
                }
                model.addAttribute("indentItemPartyGroupData", indentItemPartyGroupRequestModels);
                model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));
                model.addAttribute("tax", MastroApplicationUtils.roundTwoDecimals(tax));
                Double finalTotal = subTotal + tax;
                model.addAttribute("finalTotal", MastroApplicationUtils.roundTwoDecimals(finalTotal));
            }

            return "views/purchaseOrderPreview";

        } catch (Exception e) {
            logger.error("Error occured while getPurchaseOrderPreview :" + poId, e, PurchaseOrderController.class);
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
        logger.info("Going to approve po" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus(Constants.STATUS_APPROVED);
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            return new GenericResponse(true, "approve po");

        } catch (Exception e) {
            logger.error("Error Occured on approve po:", e, this);

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
        logger.info("Going to Review po" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus(Constants.STATUS_REVIEWED);
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            return new GenericResponse(true, "poReview");

        } catch (Exception e) {
            logger.error("Error Occured on poReview :", e, this);

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
        logger.info("Going to poDiscard po" + poId);
        try {

            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            User user = userController.getCurrentUser();
            purchaseOrder.setUser(user);
            purchaseOrder.setStatus(Constants.STATUS_DISCARD);
            purchaseOrder.setReason(reason);
            purchaseOrderRepository.save(purchaseOrder);
            purchaseOrderService.poDiscardChange(purchaseOrder.getId());
            return new GenericResponse(true, "poDiscard ");

        } catch (Exception e) {
            logger.error("Error Occured on poDiscard :", e, this);

            throw e;
        }

    }

    @RequestMapping(value = "/getPurchaseOrderOnReview", method = RequestMethod.GET)
    public String getPurchaseOrderOnReview(HttpServletRequest request, @RequestParam("indentId") Long indentId, @RequestParam("poId") Long poId, Model model) {
        logger.info("Going to get indent :" + indentId);
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
            logger.error("Error occured while getPurchaseOrderViaIndent :" + indentId, e);
            throw e;
        }
    }

    @RequestMapping(value = "/splitIndentItemReviewEdit", method = RequestMethod.GET)
    public String getsplitIndentItemReviewEdit(HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId, @RequestParam("indentId") Long indentId, @RequestParam("poId") Long poId, Model model) {
        logger.info("Going to get indent item supplyers edit:" + indentItemId);
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

            Double qtyRequired = 0.0d;
            if (itemStockDetails.getPurchaseQuantity() != null) {
                qtyRequired = itemStockDetails.getQuantityToIndent() - itemStockDetails.getPurchaseQuantity();
            } else {
                qtyRequired = itemStockDetails.getQuantityToIndent();
            }
            model.addAttribute("qtyRequired", qtyRequired);
            return "views/splitIndentItemonReviewEdit";

        } catch (Exception e) {
            logger.error("Error occured while get indent item split :" + indentItemId, e);
            throw e;
        }
    }

    @PostMapping("/saveIndentItemGroupDataOnEdit")
    public String saveIndentItemGroupDataOnEdit(@ModelAttribute("indentItemPartyGroupForm") @Valid IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel, HttpServletRequest request, Model model) {
        logger.info("Going to save additional  details in edit: " + indentItemPartyGroupRequestModel.toString());
        String poId = request.getParameter("poId");
        try {
            purchaseOrderService.IndentItemGroupDatasInEdit(indentItemPartyGroupRequestModel);
            return "redirect:/purchase/getPurchaseOrderOnReview?indentId=" + indentItemPartyGroupRequestModel.getIndentId() + "&poId=" + poId;
        } catch (ModelNotFoundException e) {
            logger.error("indentItemPartyGroupRequestModel empty", e, this);
            return "redirect:/purchase/getPurchaseOrderOnReview?indentId=" + indentItemPartyGroupRequestModel.getIndentId() + "&poId=" + poId;
        } catch (Exception e) {
            logger.error("Error occured while save indent item group details in edit : ", e);
            throw e;
        }

    }

    @PostMapping("/editPO")
    public String editPO(HttpServletRequest request, Model model) {
        String indentId = request.getParameter("purchaseIndentId");
        String purchaseId = request.getParameter("purchasesId");
        logger.info("Going to edit purchase order for the indent: " + indentId);
        try {
            purchaseOrderService.editGeneratePurchaseOrders(indentId, purchaseId);
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            logger.error("Error occured while edit purchase orders : ", e);
            throw e;
        }

    }

    @GetMapping("/getPurchaseOrderGRN")
    public String getPurchaseOrderGRN(Model model, @RequestParam("poId") Long poId, HttpServletRequest req) {
        logger.info("Going to get po GRNS : po id is" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("purchaseOrderDetails", purchaseOrder);
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            List<GRN> poGRNs = purchaseOrder.getGrnSet().stream()
                    .filter(grn -> (null != grn))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_INITIAL)))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_DISCARD)))
                    .filter(grn -> (grn.getBranch().getId().equals(currentBranch.getId())))
                    .sorted(Comparator.comparing(
                            GRN::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("grnList", poGRNs);
            return "views/POGRNList";
        } catch (Exception e) {
            logger.error("Error occured while getting po grns " + poId, e);
            throw e;
        }

    }

    @GetMapping("/getPOGRNView")
    @ResponseBody
    public GenericResponse getPOGRNView(Model model, HttpServletRequest request, @RequestParam("poGRNId") Long grnId) {

        try {
            logger.info("Going to get po GRN view : pogrn id is" + grnId);
            GRN grn = grnService.getGRNById(grnId);
            List<GRNRequestModel.GRNPOItemsModel> poGRNItemsModelList = new ArrayList<>();
            Double subTotal = 0d;
            Double tax = 0d;
            for (GRNItems grnItems : grn.getGrnItems()) {
                GRNRequestModel.GRNPOItemsModel grnpoItemsModel = new GRNRequestModel.GRNPOItemsModel();
                grnpoItemsModel.setAccepted(grnItems.getAccepted());
                grnpoItemsModel.setDiscount(grnItems.getDiscount());
                if (grnItems.getPending() <= 0) {
                    grnpoItemsModel.setPending(0.0d);
                } else {
                    grnpoItemsModel.setPending(grnItems.getPending());
                }
                grnpoItemsModel.setReceived(grnItems.getReceived());
                grnpoItemsModel.setRejected(grnItems.getRejected());
                grnpoItemsModel.setShortage(grnItems.getShortage());
                grnpoItemsModel.setQuantityDc(grnItems.getQuantityDc());
                grnpoItemsModel.setRate(grnItems.getIndentItemPartyGroup().getRate());
                grnpoItemsModel.setNameOfProduct(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductName());
                grnpoItemsModel.setUomBase(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getUom().getUOM());
                grnpoItemsModel.setUomPurchase(grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM().getUOM());
                grnpoItemsModel.setItemTotal(grnItems.getTotalPrice());
                grnpoItemsModel.setItemSgstAmt(grnItems.getSgstAmount());
                grnpoItemsModel.setItemCgstAmt(grnItems.getCgstAmount());
                grnpoItemsModel.setItemCessAmt(grnItems.getCessAmount());
                grnpoItemsModel.setHsncode(grnItems.getHsnCode());

                subTotal = subTotal + grnItems.getTotalPrice();
                Double taxCalculationPercentage = 0d;
                taxCalculationPercentage = grnItems.getCgstRate() + grnItems.getSgstRate() + grnItems.getCessRate();
                tax = tax + ((grnItems.getTotalPrice() * taxCalculationPercentage) / 100);
                poGRNItemsModelList.add(grnpoItemsModel);
            }
            Double finalTotal = subTotal + tax;
            return new GenericResponse(true, "get po grn details")
                    .setProperty("poGRNItemsModelList", poGRNItemsModelList)
                    .setProperty("totalPrice", MastroApplicationUtils.roundTwoDecimals(subTotal))
                    .setProperty("tax", MastroApplicationUtils.roundTwoDecimals(tax))
                    .setProperty("finalTotal", MastroApplicationUtils.roundTwoDecimals(finalTotal));
        } catch (Exception e) {
            logger.error("Error Occured while getting po grn view:", e, this);
            return new GenericResponse(false, e.getMessage());
        }
    }

    /**
     * Method to create new po for pending
     *
     * @param request
     * @param poId
     * @param model
     * @return po list
     */
    @RequestMapping(value = "/getPurchaseOrderForPending", method = RequestMethod.GET)
    public String getPurchaseOrderForPending(HttpServletRequest request, @RequestParam("poId") Long poId, Model model) throws JsonProcessingException {
        MastroLogUtils.info(PurchaseOrderController.class, "Going to create po for pending Items :" + poId);
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);

            PurchaseOrder shortagePO = new PurchaseOrder();
            String[] ignoreProperties = {"id", "status", "poNo", "grnSet", "itemStockDetailsSet", "indentItemPartyGroups", "poInvoices"};
            BeanUtils.copyProperties(purchaseOrder, shortagePO, ignoreProperties);
            shortagePO.setStatus(Constants.STATUS_DRAFT);
            purchaseOrderRepository.save(shortagePO);
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            String currentBranchCode = currentBranch.getBranchCode();
            shortagePO.setPoNo(MastroApplicationUtils.generateName(currentBranchCode, "PO", shortagePO.getId()));
            purchaseOrderRepository.save(shortagePO);

            Set<GRN> grnSet = purchaseOrder.getGrnSet().stream()
                    .filter(grn -> (null != grn))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_INITIAL)))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_DISCARD)))
                    .collect(Collectors.toSet());
            Set<GRNItems> grnItemsSet = new HashSet<>();
            for (GRN grn : grnSet) {
                Set<GRNItems> grnItems = grn.getGrnItems().stream()
                        .filter(grnItem -> (null != grnItem))
                        .filter(grnItem -> (!(grnItem.getPending() <= 0)))
                        .collect(Collectors.toSet());
                grnItemsSet.addAll(grnItems);
                grn.setCreateAnotherPO(0);
                grnRepository.save(grn);
            }

            for (GRNItems grnItems : grnItemsSet) {
                IndentItemPartyGroup itemPartyGroup = new IndentItemPartyGroup();
                itemPartyGroup.setEnabled(true);
                itemPartyGroup.setQuantity(grnItems.getPending());
                itemPartyGroup.setPurchaseOrder(shortagePO);
                itemPartyGroup.setParty(grnItems.getIndentItemPartyGroup().getParty());
                itemPartyGroup.setBranch(grnItems.getGrn().getBranch());
                itemPartyGroup.setRate(grnItems.getIndentItemPartyGroup().getRate());
                itemPartyGroup.setIndent(grnItems.getIndentItemPartyGroup().getIndent());
                itemPartyGroup.setItemStockDetails(grnItems.getIndentItemPartyGroup().getItemStockDetails());
                itemPartyGroup.setHsnCode(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getHsn().getHsnCode());
                itemPartyGroup.setSgstRate(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getHsn().getSgst());
                itemPartyGroup.setCgstRate(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getHsn().getCgst());
                if (itemPartyGroup.getItemStockDetails().getStock().getProduct().getHsn().getCess() != null) {
                    itemPartyGroup.setCessRate(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getHsn().getCess());
                } else {
                    itemPartyGroup.setCessRate(0.0);
                }
                indentItemPartyGroupRepository.save(itemPartyGroup);
            }
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, e.getMessage());
            throw e;
        }
    }
}
