 package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.GatePass;

import com.erp.mastro.model.request.GatePassRequestModel;
import com.erp.mastro.service.interfaces.GatePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

 @Controller
public class GatePassController {

    @Autowired
    private GatePassService gatePassService;


     /**
      * Method to get  Gate Pass
      * *
      * @param model
      * @return Gate Pass list
      */
    @RequestMapping("/inventory/getGatePass")
    public String getGatePass(Model model) {

        try {
            List<GatePass> gatePassList = gatePassService.getAllGatePass().stream()
                    .filter(gatePassData -> (null != gatePassData))
                    .filter(gatePassData -> (1 != gatePassData.getGatepassDeleteStatus()))
                    .sorted(Comparator.comparing(
                            GatePass::getId).reversed())
                    .collect(Collectors.toList());

            model.addAttribute("gatePassForm", new GatePassRequestModel());
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("gatePassTab", "gatePass");
            model.addAttribute("gatePassList", gatePassList);
            return "views/gatepass";
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while getting Gate Pass list : {}", e);
            throw e;
        }
    }

     /**
      * Method to add new GatePass
      *
      * @param model
      * @return to the create gatepass page
      */
    @GetMapping("/inventory/addGatePass")
    public String addGatePass(Model model) {
        MastroLogUtils.info(GatePassController.class, "Going to add Gate Pass list : {}");

        try {

            model.addAttribute("gatePassForm", new GatePassRequestModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("gatePassTab", "gatePassTab");

            return "views/create_gatepass";
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while adding Gate Pass : {}");
            throw e;
        }
    }


     /**
      * Method to get gate pass for edit
      *
      * @param model
      * @param request
      * @return edited gatepass details
      */

    @RequestMapping(value = "/inventory/getGatePassEdit", method = RequestMethod.GET)
    public String getGatePassEdit(HttpServletRequest request, @RequestParam("gatePassid") Long gatePassId, Model model) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to edit Gate Pass in controller: {}" + gatePassId);
        try {
            if (gatePassId != null) {
                System.out.println("gatePassId: " + gatePassId);
                model.addAttribute("inventoryModule", "inventoryModule");
                model.addAttribute("gatePassTab", "gatePassTab");
                model.addAttribute("gatePassForm", new GatePassRequestModel(gatePassService.getGatePassId(gatePassId)));
                 }
            return "views/edit_gatepass";
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while editing Gate Pass : {}"+ gatePassId);
            throw e;
        }
    }

     /**
      * Method to save and edit gatePass
      *
      * @param gatePassRequestModel
      * @param request
      * @param model
      * @return saved gate pass list
      */

    @PostMapping("/inventory/saveGatePass")
    public String saveGatePass(@RequestParam("vehicleMovement") String value, @ModelAttribute("gatePassForm") @Valid GatePassRequestModel gatePassRequestModel, HttpServletRequest request, Model model, @RequestParam("emptyMatrial") String val) {

        MastroLogUtils.info(GatePassController.class, "Going to save Gate Pass : {}");
        try {
            System.out.println("Date"+gatePassRequestModel.getsEntryDate());
            gatePassService.saveOrUpdateGatePass(gatePassRequestModel,value,val);
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while saving Gate Pass : {}");
        }
        return "redirect:/inventory/getGatePass";
    }

     /**
      * Method to view Gate Pass
      * @param request
      * @param model
      * @return gatepass details to view
      */

    @GetMapping("/inventory/viewGatePass")
    public String viewGatePass(Model model, @RequestParam("gatePassid") Long gatePassId, HttpServletRequest request) {
        MastroLogUtils.info(GatePassController.class, "Going to view gatePass :{}"+gatePassId);
        try {
            GatePass gatePass = gatePassService.getGatePassId(gatePassId);
            model.addAttribute("gatePassForm", gatePass);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("gatePassTab", "gatePassTab");
            return "views/view_gatepass";
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while viewing gatepass : {}", e);
            throw e;
        }

    }


     /**
      * Method to delete Gatepass
      *
      * @param model
      * @param request
      * @param gatePassid
      * @return deleted Gatepass list
      */

    @PostMapping("/inventory/deleteGatePass")
    @ResponseBody
    public GenericResponse deleteGatePass(Model model, HttpServletRequest request, @RequestParam("gatepassids") Long gatePassid) {
        MastroLogUtils.info(BranchController.class, "Going to delete Branch : {}" + gatePassid);
        try {
            if (gatePassid != null) {
                gatePassService.deleteGatePass(gatePassid);
                return new GenericResponse(true, "delete Gate pass Details");
            } else {
                return new GenericResponse(false, "gatepass id null");
            }
        } catch (Exception e) {
            MastroLogUtils.error(GatePassController.class, "Error occured while deleting Branch : {}");
            return new GenericResponse(false, e.getMessage());
        }
    }


}
