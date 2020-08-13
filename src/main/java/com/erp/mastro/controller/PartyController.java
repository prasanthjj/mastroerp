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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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
            List<Party> partyList = partyService.getAllPartys().stream()
                    .filter(partydata -> (null != partydata))
                    .sorted(Comparator.comparing(Party::getId).reversed())
                    .collect(Collectors.toList());

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
     * @param industryTypeRequestModel
     * @param request
     * @param model
     * @return
     */

    @RequestMapping(value = "/saveIndustryType", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse saveIndustryType(@ModelAttribute("industryForm") @Valid IndustryTypeRequestModel industryTypeRequestModel, HttpServletRequest request, Model model) {

        try {

            MastroLogUtils.info(PartyController.class, "Going to save industry type : {}");
            IndustryType industryType = partyService.saveOrUpdateIndustryType(industryTypeRequestModel);
            Set<IndustryType> industryTypeSet = new HashSet<>();
            for (IndustryType industryType1 : partyService.getAllIndustryType()) {
                industryTypeSet.add(industryType1);
            }

            Set<IndustryTypeRequestModel> industryTypeRequestModelSet = new HashSet<>();
            for (IndustryType industryType2 : industryTypeSet) {
                IndustryTypeRequestModel indusModel1 = new IndustryTypeRequestModel();
                indusModel1.setId(industryType2.getId());
                indusModel1.setIndustryType(industryType2.getIndustryType());
                industryTypeRequestModelSet.add(indusModel1);
            }

            return new GenericResponse(true, "save industry type")
                    .setProperty("industryid", industryType.getId())
                    .setProperty("industryType", industryType.getIndustryType())
                    .setProperty("fullindustrytypes", industryTypeRequestModelSet);
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(PartyController.class, "Error occured while saving industrytype :{}", e);
            return new GenericResponse(false, "industrytype model not found");

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
    public String saveParty(@ModelAttribute("partyForm") @Valid PartyRequestModel partyRequestModel, HttpServletRequest request, Model model) throws ParseException {
        MastroLogUtils.info(PartyController.class, "Going to save party type :{}");
        try {
            String[] branchIds = request.getParameterValues("branchId");
            String[] creditLimits = new String[partyRequestModel.getCreditDetailsModelList().size()];
            String[] creditDays = new String[partyRequestModel.getCreditDetailsModelList().size()];
            String[] creditWorthiness = new String[partyRequestModel.getCreditDetailsModelList().size()];
            String[] interestRates = new String[partyRequestModel.getCreditDetailsModelList().size()];
            String[] remarks = new String[partyRequestModel.getCreditDetailsModelList().size()];
            for (int i = 0; i < partyRequestModel.getCreditDetailsModelList().size(); i++) {
                creditLimits[i] = request.getParameter("creditDetailsModelList[" + i + "].creditLimit");
                creditDays[i] = request.getParameter("creditDetailsModelList[" + i + "].creditDays");
                creditWorthiness[i] = request.getParameter("creditDetailsModelList[" + i + "].creditWorthiness");
                interestRates[i] = request.getParameter("creditDetailsModelList[" + i + "].interestRate");
                remarks[i] = request.getParameter("creditDetailsModelList[" + i + "].remarks");
            }
            String[] creditLimits1 = request.getParameterValues("creditLimit");
            String[] creditDays1 = request.getParameterValues("creditDays");
            String[] creditWorthiness1 = request.getParameterValues("creditWorthiness");
            String[] interestRates1 = request.getParameterValues("interestRates");
            String[] remarks1 = request.getParameterValues("remarks");

            String[] stringcreditlimit = ArrayUtils.addAll(creditLimits, creditLimits1);
            String[] stringdays = ArrayUtils.addAll(creditDays, creditDays1);
            String[] stringworthiness = ArrayUtils.addAll(creditWorthiness, creditWorthiness1);
            String[] stringinterestRates = ArrayUtils.addAll(interestRates, interestRates1);
            String[] stringremarks = ArrayUtils.addAll(remarks, remarks1);

            partyService.saveOrUpdateParty(partyRequestModel, branchIds, stringcreditlimit, stringdays, stringworthiness, stringinterestRates, stringremarks);
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
            Iterator<Branch> finalSet = branchList.iterator();
            for (Iterator<Branch> it = finalSet; it.hasNext(); ) {
                Branch fullModel = it.next();
                if (fullModel != null) {
                    for (Branch branchModel : branches) {
                        if (fullModel.getId() == branchModel.getId()) {
                            finalSet.remove();
                        }
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

    /**
     * Activate or Deactivate Party
     *
     * @param model
     * @param request
     * @param partyId
     * @return
     */

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
