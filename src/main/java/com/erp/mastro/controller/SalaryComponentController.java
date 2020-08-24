package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.SalaryComponent;
import com.erp.mastro.model.request.SalaryComponentRequestModel;
import com.erp.mastro.service.interfaces.SalaryComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/hr")
@Controller
public class SalaryComponentController {

    @Autowired
    private SalaryComponentService salaryComponentService;

    /**
     * Method to get Salary Component
     *
     * @param model
     * @return salary component list
     */
    @RequestMapping("/getSalaryComponent")
    public String getSalaryComponent(Model model) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to get Salary component list : {}");
        try {

            List<SalaryComponent> salaryComponentList = new ArrayList<>();
            for (SalaryComponent salaryComponent : salaryComponentService.getAllSalaryComonents()) {
                /*  if (salaryComponent.isative() != 1) {*/
                salaryComponentList.add(salaryComponent);
                /*  }*/
            }
            model.addAttribute("salryComponentForm", new SalaryComponentRequestModel());
            model.addAttribute("hrModule", "hrModule");
            model.addAttribute("salaryComponentTab", "salaryComponent");
            model.addAttribute("salaryComponentList", salaryComponentList);
            return "views/salary_components_master";
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while getting Salary Component list : {}", e);
            throw e;
        }
    }

    @GetMapping("/addSalaryComponent")
    public String addSalaryComponent(Model model) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to add Salary component list : {}");

        try {
            List<SalaryComponent> salaryComponentList = salaryComponentService.getAllSalaryComonents().stream()
                    .filter(salarycomponentData -> (null != salarycomponentData))

                    .sorted(Comparator.comparing(
                            SalaryComponent::getId).reversed())
                    .collect(Collectors.toList());

            model.addAttribute("salryComponentForm", new SalaryComponentRequestModel());
            model.addAttribute("hrModule", "hrModule");
            model.addAttribute("salaryComponentTab", "salaryComponent");

            return "views/add_component";
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while adding Salry component : {}");
            throw e;
        }

    }

    /**
     * Method to get salary Component for edit
     *
     * @param model
     * @param request
     * @param salryComponentId
     * @return edited salarycomponent details
     */
    @RequestMapping(value = "/getSalaryComponentForEdit", method = RequestMethod.GET)
    public String getSalaryComponentForEdit(HttpServletRequest request, @RequestParam("salrycomponentid") Long salryComponentId, Model model) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to edit salry Component : {}" + salryComponentId);
        try {
            if (salryComponentId != null) {

                SalaryComponent salaryComponent = salaryComponentService.getSalaryComponentId(salryComponentId);
                model.addAttribute("hrModule", "hrModule");
                model.addAttribute("salaryComponentTab", "salaryComponent");
                model.addAttribute("salryComponentForm", new SalaryComponentRequestModel(salaryComponentService.getSalaryComponentId(salryComponentId)));

            }
            return "views/edit_component";
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while editing salry Component : {}" + salryComponentId);
            throw e;
        }
    }

    /**
     * Method to save and edit Salry component
     *
     * @param salaryComponentRequestModel
     * @param request
     * @param model
     * @return saved salry in salrycomponent list
     */
    @PostMapping("/saveSalryComponent")
    public String saveSalryComponent(@RequestParam("calculationType") String value, @ModelAttribute("salryComponentForm") @Valid SalaryComponentRequestModel salaryComponentRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to save Salry Component : {}");
        try {
            salaryComponentService.saveOrUpdateSalaryComponent(salaryComponentRequestModel, value);
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while saving Salary Component : {}");
        }
        return "redirect:/hr/getSalaryComponent";
    }

    @GetMapping("/activateOrDeactivateSalaryComponent")
    @ResponseBody
    public GenericResponse activateOrDeactivateSalaryComponent(Model model, HttpServletRequest request, @RequestParam("salryComponentId") Long salryComponentId) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to Activate or Deactivate Component :{}" + salryComponentId);
        SalaryComponent salarycomponentDetails = salaryComponentService.getSalaryComponentId(salryComponentId);

        salaryComponentService.activateOrDeactivateSalaryComponent(salryComponentId);

        return new GenericResponse(true, "get Salary details")
                .setProperty("salryComponentId", salarycomponentDetails.getId());
    }

    @GetMapping("/viewSalaryComponent")
    public String viewSalaryComponent(Model model, @RequestParam("salrycomponentid") Long salryComponentId, HttpServletRequest request) {
        MastroLogUtils.info(SalaryComponentController.class, "Going to view Salry Component :{}" + salryComponentId);
        try {
            SalaryComponent salaryComponent = salaryComponentService.getSalaryComponentId(salryComponentId);
            //model.addAttribute("assetDetails", assets);
            model.addAttribute("salryComponentForm", salaryComponentService.getSalaryComponentId(salryComponentId));
            model.addAttribute("hrModule", "hrModule");
            model.addAttribute("salaryComponentTab", "salaryComponent");
            return "views/ViewSalaryComponent";
        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while viewing SalaryComponent : {}", e);
            throw e;
        }

    }
}
