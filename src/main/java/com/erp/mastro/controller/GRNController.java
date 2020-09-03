package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.GRN;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.PurchaseOrder;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;
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

    /**
     * Method to get all GRN
     *
     * @param model
     * @return GRN list
     */
    @GetMapping("/getGRNList")
    public String getGRNList(Model model) {
        MastroLogUtils.info(GRNController.class, "Going to get GRN list: {}");
        try {
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            model.addAttribute("grnForm", new GRNRequestModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("GRNTab", "GRN");
            List<GRN> grnList = grnService.getAllGRNs().stream()
                    .filter(grn -> (null != grn))
                    .filter(grn -> (!grn.getStatus().equals("Discard")))
                    .filter(grn -> (grn.getBranch().getId().equals(currentBranch.getId())))
                    .sorted(Comparator.comparing(
                            GRN::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("grnList", grnList);

            return "views/GRNMaster";

        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occured while getting GRN list: {}", e);
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
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("GRNTab", "GRN");
            return "views/addGRN";

        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occured while creating GRN : {}");
            throw e;
        }

    }

    /**
     * Method to get party pos
     *
     * @param partyId
     * @return party pos list
     */
    @RequestMapping(value = "/getPartyPo", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartyPo(@RequestParam("partyId") Long partyId) {
        try {
            MastroLogUtils.info(GRNController.class, "Going to get party pos : {}");
            Party party = partyService.getPartyById(partyId);
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<PurchaseOrder> purchaseOrderSet = party.getPurchaseOrders().stream()
                    .filter(poData -> (null != poData))
                    .filter(po -> (po.getStatus().equals("Approve")))
                    .filter(po -> (po.getIndent().getBranch().getId().equals(currentBranch.getId())))
                    .collect(Collectors.toSet());

            Set<PartyRequestModel.partyPoModel> partyPoModels = new HashSet<PartyRequestModel.partyPoModel>();
            for (PurchaseOrder po : purchaseOrderSet) {
                PartyRequestModel.partyPoModel partyPoModel = new PartyRequestModel.partyPoModel();
                partyPoModel.setId(po.getId());
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
        MastroLogUtils.info(GRNController.class, "Going to create basic grn details : {}" + grnRequestModel.toString());
        try {
            model.addAttribute("inventoryModule", "inventoryModule");
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
        MastroLogUtils.info(GRNController.class, "Going to save grn item details: {}" + grnRequestModel.toString());
        try {
            Long grnId = Long.parseLong(request.getParameter("grnIds"));
            grnService.saveOrUpdateGRNItemDetails(grnRequestModel, grnId);
            return "redirect:/inventory/getGRNList";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "grn model empty", e);
            return "redirect:/inventory/getGRNList";
        } catch (Exception e) {
            MastroLogUtils.error(GRNController.class, "Error occured while save grn item details : {}", e);
            throw e;
        }

    }
}
