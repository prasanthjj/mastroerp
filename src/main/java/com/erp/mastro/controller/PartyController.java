package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Party;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/getPartys")
    public String getPartys(Model model) {

        try {
            List<Party> partyList = partyService.getAllPartys();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partyTab", "party");
            model.addAttribute("partyList", partyList);
            return "views/partyMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/getCreateParty")
    public String getCreateParty(Model model) {

        try {
            model.addAttribute("partyForm", new PartyRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partyTab", "party");
            return "views/createPartyMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/saveParty")
    public String saveParty(@ModelAttribute("hsnForm") @Valid PartyRequestModel partyRequestModel, HttpServletRequest request, Model model) {

        try {
            partyService.saveOrUpdateParty(partyRequestModel);
            return "redirect:/master/getPartys";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/deletePartyDetails")
    @ResponseBody
    public GenericResponse deletePartyDetails(Model model, HttpServletRequest request, @RequestParam("partyId") Long partyId) {

        try {

            partyService.deletePartyDetails(partyId);
            return new GenericResponse(true, "delete party details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }


}
