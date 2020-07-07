package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.HSN;
import com.erp.mastro.model.request.HSNRequestModel;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/master")
public class HSNController {

    @Autowired
    private HSNService hsnService;

    @GetMapping("/getHSN")
    public String getHSN(Model model) {

        try {
            List<HSN> hsnList = new ArrayList<>();
            for (HSN hsn : hsnService.getAllHSN()) {
                if (hsn.getHsnDeleteStatus() != 1) {
                    hsnList.add(hsn);
                }
            }
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("hsnTab", "hsn");
            model.addAttribute("hsnList", hsnList);
            return "views/hsn_master";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/getCreateHSN")
    public String getCreateHSN(Model model) {

        try {
            model.addAttribute("hsnForm", new HSNRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("hsnTab", "hsn");
            return "views/createHsnMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/saveHSN")
    public String saveHSN(@ModelAttribute("hsnForm") @Valid HSNRequestModel hsnRequestModel, HttpServletRequest request, Model model) {

        try {
            hsnService.saveOrUpdateHSN(hsnRequestModel);
            return "redirect:/master/getHSN";
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/viewHSN")
    public String getViewHSN(Model model, @RequestParam("hsnId") Long hsnId, HttpServletRequest req) {

        try {
            HSN hsn = hsnService.getHSNById(hsnId);
            model.addAttribute("hsnDetails", hsn);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("hsnTab", "hsn");
            return "views/viewHsn";
        } catch (Exception e) {
            throw e;
        }

    }

    @RequestMapping(value = "/getHSNEdit", method = RequestMethod.GET)
    public String getHSNEdit(HttpServletRequest request, @RequestParam("hsnId") Long hsnId, Model model) {

        try {
            HSNRequestModel hsnRequestModel = new HSNRequestModel();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("hsnTab", "hsn");
            model.addAttribute("hsnForm", hsnService.getHSNById(hsnId));
            return "views/editHsnMaster";

        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/deleteHsnDetails")
    @ResponseBody
    public GenericResponse deleteHsnDetails(Model model, HttpServletRequest request, @RequestParam("hsnId") Long hsnId) {

        try {

            hsnService.deleteHsnDetails(hsnId);
            return new GenericResponse(true, "delete hsn details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }

}
