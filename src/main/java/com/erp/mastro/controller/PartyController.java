package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.CreditDetails;
import com.erp.mastro.entities.IndustryType;
import com.erp.mastro.entities.Party;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndustryTypeRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.PartyService;
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
/**
 * Party controller with all party operations include list,create,edit,view and edit
 */
public class PartyController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PartyRepository partyRepository;


    /**
     * The method for get the full party list
     *
     * @param model
     * @return
     */

    @GetMapping("/getPartys")
    public String getPartys(Model model) {
        MastroLogUtils.info(PartyController.class, "Going to get Party : {}");

        try {
            List<Party> partyList = new ArrayList<>();
            for (Party party : partyService.getAllPartys()) {
                partyList.add(party);
            }
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partyTab", "party");
            model.addAttribute("partyList", partyList);
            return "views/partyMaster";

        } catch (Exception e) {
            MastroLogUtils.error(PartyController.class, "Error occured while getting party :{}", e);
            throw e;
        }

    }

    /**
     * Method load to create the party
     *
     * @param model
     * @return the party creation page
     */

    @GetMapping("/getCreateParty")
    public String getCreateParty(Model model) {
        MastroLogUtils.info(PartyController.class, "Going to create party :{}");
        try {
            List<IndustryType> industryTypes = new ArrayList<>();
            for (IndustryType industryType : partyService.getAllIndustryType()) {
                industryTypes.add(industryType);
            }
            List<Branch> branchList = new ArrayList<>();
            for (Branch branch : branchService.getAllBranch()) {
                if (branch.getBranchDeleteStatus() != 1) {
                    branchList.add(branch);
                }
            }
            model.addAttribute("partyForm", new PartyRequestModel());
            model.addAttribute("industryForm", new IndustryTypeRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partyTab", "party");
            model.addAttribute("industryTypes", industryTypes);
            model.addAttribute("branchList", branchList);
            return "views/createPartyMaster";

        } catch (Exception e) {
            MastroLogUtils.error(PartyController.class, "Error occured while getting CreateParty :{}");
            throw e;
        }

    }

    /**
     * Method to save and edit industryType
     *
     * @param industryTypeRequestModel
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/saveIndustryType")
    public String saveIndustryType(@ModelAttribute("industryForm") @Valid IndustryTypeRequestModel industryTypeRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(PartyController.class, "Going to save industry type :{}");
        try {
            partyService.saveOrUpdateIndustryType(industryTypeRequestModel);
            return "redirect:/master/getCreateParty";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "IndustryRequest model empty", e);
            return "redirect:/master/getCreateParty";
        } catch (Exception e) {
            MastroLogUtils.error(PartyController.class, "Error occured while saving industrytype :{}", e);
            throw e;
        }
    }

    /**
     * Method to save party
     *
     * @param partyRequestModel
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/saveParty")
    public String saveParty(@ModelAttribute("partyForm") @Valid PartyRequestModel partyRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(PartyController.class, "Going to save party type :{}");
        try {
            String[] branchIds = request.getParameterValues("branchId");
            String[] creditLimits = request.getParameterValues("creditLimit");
            String[] creditDays = request.getParameterValues("creditDays");
            String[] creditWorthiness = request.getParameterValues("creditWorthiness");
            String[] interestRates = request.getParameterValues("interestRates");
            String[] remarks = request.getParameterValues("remarks");

            partyService.saveOrUpdateParty(partyRequestModel, branchIds, creditLimits, creditDays, creditWorthiness, interestRates, remarks);
            return "redirect:/master/getPartys";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "PartyRequestModel model empty", e);
            return "redirect:/master/getPartys";
        } catch (Exception e) {
            MastroLogUtils.error(PartyController.class, "Error occured while saving party type :{}", e);
            throw e;
        }
    }

    /**
     * Method to load the edit party page
     *
     * @param request
     * @param partyId
     * @param model
     * @return the party page for edit
     */

    @RequestMapping(value = "/getPartyEdit", method = RequestMethod.GET)
    public String getPartyEdit(HttpServletRequest request, @RequestParam("partyId") Long partyId, Model model) {
        MastroLogUtils.info(PartyController.class, "Going to edit party :{}" + partyId);
        try {
            List<IndustryType> industryTypes = new ArrayList<>();
            for (IndustryType industryType : partyService.getAllIndustryType()) {
                industryTypes.add(industryType);
            }
            List<Branch> branchList = new ArrayList<>();
            for (Branch branch : branchService.getAllBranch()) {
                if (branch.getBranchDeleteStatus() != 1) {
                    branchList.add(branch);
                }
            }
            List<Branch> branches = new ArrayList<>();
            for (CreditDetails creditDetails : partyService.getPartyById(partyId).getCreditDetails()) {
                branches.add(creditDetails.getBranch());
            }
            for (Branch branch : branchList) {
                for (Branch branch1 : branches) {
                    if (branch.getId().equals(branch1.getId())) {
                        branchList.remove(branch1);
                    }
                }
            }
            model.addAttribute("branchList", branchList);
            model.addAttribute("industryTypes", industryTypes);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partytab", "party");
            if (partyId != null) {
                model.addAttribute("partyForm", new PartyRequestModel(partyService.getPartyById(partyId)));
            }
            model.addAttribute("partyContactSize", partyService.getPartyById(partyId).getContactDetails().size());
            return "views/editPartyMaster";

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * method to view party
     *
     * @param model
     * @param partyId
     * @param req
     * @return
     */

    @GetMapping("/viewParty")
    public String getViewParty(Model model, @RequestParam("partyId") Long partyId, HttpServletRequest req) {
        MastroLogUtils.info(PartyController.class, "Going to view Party :{}" + partyId);
        try {
            Party party = partyService.getPartyById(partyId);
            model.addAttribute("partyDetails", party);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("partyTab", "party");
            return "views/viewParty";
        } catch (Exception e) {
            MastroLogUtils.error(PartyController.class, "Error occured while viewing party : {}", e);
            throw e;
        }

    }


    @PostMapping("/activateOrDeactivateParty")
    @ResponseBody
    public GenericResponse activateOrDeactivateParty(Model model, HttpServletRequest request, @RequestParam("partyId") Long partyId) {
        MastroLogUtils.info(PartyController.class, "Going to delete Party : {}");
        Party party = partyService.getPartyById(partyId);
        partyService.activateOrDeactivateParty(partyId);

        return new GenericResponse(true, "get Party details")
                .setProperty("partyId", party.getId());

    }

}
