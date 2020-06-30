package com.erp.mastro.controller;

import com.erp.mastro.entities.HSN;
import com.erp.mastro.model.request.HSNRequestModel;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class HSNController {

    @Autowired
    private HSNService hsnService;

    @GetMapping("/master/getHSN")
    public String getHSN(Model model) {

        try {
            List<HSN> hsnList = hsnService.getAllHSN();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("hsnTab", "hsn");
            model.addAttribute("hsnList", hsnList);
            return "views/hsn_master";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/master/getCreateHSN")
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

    @PostMapping("/master/saveHSN")
    public String saveHSN(@ModelAttribute("hsnForm") @Valid HSNRequestModel hsnRequestModel, HttpServletRequest request, Model model) {

        try {
            hsnService.saveOrUpdateHSN(hsnRequestModel);
            return "redirect:/master/getHSN";
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/master/viewHSN")
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

}
