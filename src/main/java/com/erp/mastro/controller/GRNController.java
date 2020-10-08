package com.erp.mastro.controller;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.repository.GRNRepository;
import com.erp.mastro.repository.IndentItemPartyGroupRepository;
import com.erp.mastro.service.interfaces.GRNService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.UserService;
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
 * Controller include all GRN operations
 */
@Controller
@RequestMapping("/inventory")
public class GRNController {

    @Autowired
    private UserController userController;

    @Autowired
    private PartyService partyService;

    @Autowired
    private GRNService grnService;

    @Autowired
    private UserService userService;

    @Autowired
    private GRNRepository grnRepository;

    @Autowired
    private IndentItemPartyGroupRepository indentItemPartyGroupRepository;

    /**
     * Method to get all GRN and display as a list
     *
     * @param model
     * @return GRN list
     */
    @GetMapping("/getGRNList")
    public String getGRNList(Model model) {
        MastroLogUtils.info(GRNController.class, "In GRNController.getGRNList()");
        try {
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            model.addAttribute("grnForm", new GRNRequestModel());
            model.addAttribute(Constants.INVENTORY_MODULE, Constants.INVENTORY_MODULE);
            model.addAttribute("GRNTab", "GRN");
            List<GRN> grnList = grnService.getAllGRNs().stream()
                    .filter(grn -> (null != grn))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_INITIAL)))
                    .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_DISCARD)))
                    .filter(grn -> (grn.getBranch().getId().equals(currentBranch.getId())))
                    .sorted(Comparator.comparing(
                            GRN::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("grnList", grnList);

            return "views/GRNMaster";

        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occurred while getting GRN list", e);
            throw e;
        }

    }

    /**
     * Method to get create GRN
     *
     * @param model
     * @return GRN creation page
     */
    @GetMapping("/getCreateGRN")
    public String getCreateGRN(Model model) {
        MastroLogUtils.info(GRNController.class, "Going to get Create GRN : {}");
        try {
            model.addAttribute("grnForm", new GRNRequestModel());
            model.addAttribute(Constants.INVENTORY_MODULE, Constants.INVENTORY_MODULE);
            model.addAttribute("GRNTab", "GRN");
            return "views/addGRN";

        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occured while creating GRN : {}");
            throw e;
        }

    }

    /**
     * Method to get party PO List
     *
     * @param partyId
     * @return party pos list
     */
    @RequestMapping(value = "/getPartyPo", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartyPo(@RequestParam("partyId") Long partyId) {
        try {
            MastroLogUtils.info(GRNController.class, "Going to get party pos for party id " + partyId);
            Party party = partyService.getPartyById(partyId);
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<PurchaseOrder> purchaseOrderSet = party.getPurchaseOrders().stream()
                    .filter(poData -> (null != poData))
                    .filter(po -> (po.getStatus().equals(Constants.STATUS_APPROVED)))
                    .filter(po -> (po.getIndent().getBranch().getId().equals(currentBranch.getId())))
                    .collect(Collectors.toSet());

            Set<PartyRequestModel.partyPoModel> partyPoModels = new HashSet<PartyRequestModel.partyPoModel>();
            for (PurchaseOrder po : purchaseOrderSet) {
                PartyRequestModel.partyPoModel partyPoModel = new PartyRequestModel.partyPoModel();
                partyPoModel.setId(po.getId());
                partyPoModel.setPono(po.getPoNo());
                partyPoModels.add(partyPoModel);
            }

            return new GenericResponse(true, "get party pos")
                    .setProperty("partypos", partyPoModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to save grn basic data
     *
     * @param grnRequestModel
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/createGRNBasic")
    public String createGRNBasic(@ModelAttribute("grnForm") @Valid GRNRequestModel grnRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(GRNController.class, "Going to create basic GRN details :" + grnRequestModel.toString());
        try {
            model.addAttribute(Constants.INVENTORY_MODULE, Constants.INVENTORY_MODULE);
            model.addAttribute("GRNTab", "GRN");
            GRN grn = grnService.createGRN(grnRequestModel);
            model.addAttribute("grnDetails", grn);
            model.addAttribute("grnForm", new GRNRequestModel(grnService.getGRNById(grn.getId())));
            return "views/addGRN";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "GRNmodel empty", e);
            return "views/addGRN";
        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * Method to save grn item details
     *
     * @param grnRequestModel
     * @param request
     * @param model
     * @return the list
     */
    @PostMapping("/saveGRNDetails")
    public String saveGRNDetails(@ModelAttribute("grnForm") @Valid GRNRequestModel grnRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(GRNController.class, "In GRNController.saveGRNDetails() method :" + grnRequestModel.toString());
        try {
            Long grnId = Long.parseLong(request.getParameter("grnIds"));
            grnService.saveOrUpdateGRNItemDetails(grnRequestModel, grnId);
            return "redirect:/inventory/getGRNList";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "grn model empty", e);
            return "redirect:/inventory/getGRNList";
        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occurred while save grn item details : {}", e);
            throw e;
        }

    }

    @RequestMapping(value = "/getGRNPreview", method = RequestMethod.GET)
    public String getGRNPreview(HttpServletRequest request, @RequestParam("grnId") Long grnId, Model model) {
        MastroLogUtils.info(GRNController.class, "Going to get grnPreview :{}" + grnId);
        try {
            model.addAttribute(Constants.INVENTORY_MODULE, Constants.INVENTORY_MODULE);
            model.addAttribute("GRNTab", "GRN");
            if (grnId != null) {
                GRN grn = grnService.getGRNById(grnId);
                model.addAttribute("grnDetails", grn);
                Party party = grn.getParty();
                ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                        .findFirst().get();
                model.addAttribute("partyContactDetails", contactDetails);
                Double subTotal = 0d;
                Double tax = 0d;
                for (GRNItems grnItems : grn.getGrnItems()) {
                    subTotal = subTotal + grnItems.getTotalPrice();
                    Double taxCalculationPercentage = 0d;
                    taxCalculationPercentage = grnItems.getCgstRate() + grnItems.getSgstRate() + grnItems.getCessRate();
                    tax = tax + ((grnItems.getTotalPrice() * taxCalculationPercentage) / 100);
                }
                model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));
                model.addAttribute("tax", MastroApplicationUtils.roundTwoDecimals(tax));
                Double finalTotal = subTotal + tax;
                model.addAttribute("finalTotal", MastroApplicationUtils.roundTwoDecimals(finalTotal));
            }

            return "views/GRNPreview";

        } catch (Exception e) {
            MastroLogUtils.error(PurchaseOrderController.class, "Error occured while getGRNPreview :{}" + grnId, e);
            throw e;
        }
    }

    @PostMapping("/grnApprove")
    @ResponseBody
    public GenericResponse grnApprove(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("grnids") Long grnId) {
        MastroLogUtils.info(GRNController.class, "In GRNController.grnApprove() method :" + grnId);
        try {
            GRN grn = grnService.getGRNById(grnId);
            if (grn != null) {
                grn.setStatus(Constants.STATUS_APPROVED);
                grn.setReason(reason);
                grnRepository.save(grn);
                grnService.stockUpdationBasedOnGRN(grn);
            }
            return new GenericResponse(true, "approve grn");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occurred on approve grn : ", e);

            throw e;
        }

    }

    @PostMapping("/grnReview")
    @ResponseBody
    public GenericResponse grnReview(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("grnids") Long grnId) {
        MastroLogUtils.info(GRNController.class, "Going to Review GRN" + grnId);
        try {
            GRN grn = grnService.getGRNById(grnId);
            grn.setStatus(Constants.STATUS_REVIEWED);
            grn.setReason(reason);
            grnRepository.save(grn);
            return new GenericResponse(true, "Review grn");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on Review grn:{}", e);

            throw e;
        }

    }

    @PostMapping("/grnDiscard")
    @ResponseBody
    public GenericResponse grnDiscard(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("grnids") Long grnId) {
        MastroLogUtils.info(GRNController.class, "Going to Discard GRN" + grnId);
        try {
            GRN grn = grnService.getGRNById(grnId);
            grn.setStatus(Constants.STATUS_DISCARD);
            grn.setReason(reason);
            grnRepository.save(grn);
            return new GenericResponse(true, "Discard grn");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on Discard grn:{}", e);

            throw e;
        }

    }

    /**
     * GRN tolerence check method
     *
     * @param model
     * @param request
     * @param dcQty
     * @param pendingQty
     * @param indentItemPartyGroupId
     * @return response
     */
    @GetMapping("/getGRNTolerenceCheck")
    @ResponseBody
    public GenericResponse getGRNTolerenceCheck(Model model, HttpServletRequest request, @RequestParam("dcQty") Double dcQty, @RequestParam("pendingQty") Double pendingQty, @RequestParam("indentItemPartyGroupId") Long indentItemPartyGroupId) {
        MastroLogUtils.info(GRNController.class, "Going to tplerence check for dcQty" + dcQty);
        try {
            boolean qtyDcMismatch = grnService.qtyMismatchChecking(indentItemPartyGroupId, dcQty, pendingQty);
            return new GenericResponse(true, "sucess")
                    .setProperty("qtyDcMismatch", qtyDcMismatch);

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on Tolerence check", e);

            throw e;
        }

    }
}
