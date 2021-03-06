package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/master")
public class HSNController {

    @Autowired
    private HSNService hsnService;

    @GetMapping("/getHSN")
    public String getHSN(Model model) {
        MastroLogUtils.info(HSNController.class, "Going to get all hsn : {}");
        try {
            List<HSN> hsnList = hsnService.getAllHSN().stream()
                    .filter(hsnData -> (null != hsnData))
                    .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                    .sorted(Comparator.comparing(
                            HSN::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute(Constants.MASTER_MODULE, Constants.MASTER_MODULE);
            model.addAttribute("hsnTab", "hsn");
            model.addAttribute("hsnList", hsnList);
            return "views/hsn_master";

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getting hsn : {}", e);
            throw e;
        }

    }

    @GetMapping("/getCreateHSN")
    public String getCreateHSN(Model model) {
        MastroLogUtils.info(HSNController.class, "Going to get CreateHSN :{}");
        try {
            model.addAttribute("hsnForm", new HSNRequestModel());
            model.addAttribute(Constants.MASTER_MODULE, Constants.MASTER_MODULE);
            model.addAttribute("hsnTab", "hsn");
            return "views/createHsnMaster";

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getting CreateHSN :{}", e);
            throw e;
        }

    }

    @PostMapping("/saveHSN")
    public String saveHSN(@ModelAttribute("hsnForm") @Valid HSNRequestModel hsnRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(HSNController.class, "Going to save HSN :{}");
        try {
            hsnService.saveOrUpdateHSN(hsnRequestModel);
            return "redirect:/master/getHSN";
        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while saving HSN :{}");
            throw e;
        }
    }

    @GetMapping("/viewHSN")
    public String getViewHSN(Model model, @RequestParam("hsnId") Long hsnId, HttpServletRequest req) {
        MastroLogUtils.info(HSNController.class, "Going to get viewHSN :{}" + hsnId);
        try {
            HSN hsn = hsnService.getHSNById(hsnId);
            model.addAttribute("hsnDetails", hsn);
            model.addAttribute(Constants.MASTER_MODULE, Constants.MASTER_MODULE);
            model.addAttribute("hsnTab", "hsn");
            return "views/viewHsn";
        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getting viewHsn :{}" + hsnId, e);
            throw e;
        }

    }

    @RequestMapping(value = "/getHSNEdit", method = RequestMethod.GET)
    public String getHSNEdit(HttpServletRequest request, @RequestParam("hsnId") Long hsnId, Model model) {
        MastroLogUtils.info(HSNController.class, "Going to get editHSN :{}" + hsnId);
        try {
            HSNRequestModel hsnRequestModel = new HSNRequestModel();
            model.addAttribute(Constants.MASTER_MODULE, Constants.MASTER_MODULE);
            model.addAttribute("hsnTab", "hsn");
            model.addAttribute("hsnForm", hsnService.getHSNById(hsnId));
            return "views/editHsnMaster";

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while getting editHsn :{}" + hsnId, e);
            throw e;
        }
    }

    @PostMapping("/deleteHsnDetails")
    @ResponseBody
    public GenericResponse deleteHsnDetails(Model model, HttpServletRequest request, @RequestParam("hsnId") Long hsnId) {
        MastroLogUtils.info(HSNController.class, "Going to delete HsnDetails by id :{}" + hsnId);
        try {

            hsnService.deleteHsnDetails(hsnId);
            return new GenericResponse(true, "delete hsn details");

        } catch (Exception e) {
            MastroLogUtils.error(HSNController.class, "Error occured while deletinh HSN details :{}" + hsnId, e);
            return new GenericResponse(false, e.getMessage());

        }

    }

}
