package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.model.request.BranchRequestModel;
import com.erp.mastro.service.interfaces.BranchService;
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

@Controller
@RequestMapping("/admin")
public class BranchController {

    @Autowired
    private BranchService branchService;

    /**
     * Method to add new Branch
     *
     * @param model
     * @return to the create branch page
     */
    @GetMapping("/addBranch")
    public String addBranch(Model model) {
        MastroLogUtils.info(BranchController.class, "Going to add Branch :{}");
        try {
            List<Branch> branchList = branchService.getAllBranch().stream()
                    .filter(branchData -> (null != branchData))
                    .filter(branchData -> (1 != branchData.getBranchDeleteStatus()))
                    .sorted(Comparator.comparing(
                            Branch::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("branchForm", new BranchRequestModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("branchTab", "branch");
            return "views/create_branch";
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while adding Branch : {}");
            throw e;
        }
    }

    /**
     * Method to save and edit Branch
     *
     * @param branchRequestModel
     * @param request
     * @param model
     * @return saved branch in branch list
     */
    @PostMapping("/saveBranch")
    public String saveBranch(@ModelAttribute("branchForm") @Valid BranchRequestModel branchRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(BranchController.class, "Going to save Branch : {}");
        try {
            branchService.saveOrUpdateBranch(branchRequestModel);
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while saving Branch : {}");
        }
        return "redirect:/admin/getBranch";
    }

    /**
     * Method to get Branch
     *
     * @param model
     * @return branch list
     */
    @GetMapping("/getBranch")
    public String getBranch(Model model) {
        MastroLogUtils.info(BranchController.class, "Going to get Branch list : {}");
        try {
            List<Branch> branchList = new ArrayList<>();
            for (Branch branch : branchService.getAllBranch()) {
                if (branch.getBranchDeleteStatus() != 1) {
                    branchList.add(branch);
                }
            }
            model.addAttribute("branchForm", new BranchRequestModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("branchTab", "branch");
            model.addAttribute("branchList", branchList);
            return "views/branch_master";
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while getting the Branch list : {}");
            throw e;
        }
    }

    /**
     * Method to edit Branch
     *
     * @param branchId
     * @param request
     * @param model
     * @return branch details to edit
     */
    @RequestMapping(value = "/editBranch", method = RequestMethod.GET)
    public String editBranch(HttpServletRequest request, @RequestParam("branchId") Long branchId, Model model) {
        MastroLogUtils.info(BranchController.class, "Going to edit Branch : {}" + branchId);
        try {
            if (branchId != null) {
                Branch branch = branchService.getBranchById(branchId);
                BranchRequestModel branchRequestModel = new BranchRequestModel(branch);
                model.addAttribute("adminModule", "adminModule");
                model.addAttribute("branchTab", "branch");
                model.addAttribute("branchForm", branchRequestModel);
            }
            return "views/edit_branch";
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while editing Branch : {}");
            throw e;
        }
    }

    /**
     * Method to view Branch
     *
     * @param branchId
     * @param request
     * @param model
     * @return branch details to view
     */
    @GetMapping("/viewBranch")
    public String getViewBranch(Model model, @RequestParam("branchId") Long branchId, HttpServletRequest request) {
        MastroLogUtils.info(BranchController.class, "Going to view Branch : {}" + branchId);
        try {
            model.addAttribute("branchForm", branchService.getBranchById(branchId));
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("branchTab", "branch");
            return "views/view_branch";
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while viewing Branch : {}");
            throw e;
        }
    }

    /**
     * Method to delete Branch
     *
     * @param model
     * @param request
     * @param branchId
     * @return deleted Branch list
     */
    @PostMapping("/deleteBranchDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("branchId") Long branchId) {
        MastroLogUtils.info(BranchController.class, "Going to delete Branch : {}" + branchId);
        try {
            if (branchId != null) {
                branchService.deleteBranchDetails(branchId);
                return new GenericResponse(true, "delete Branch Details");
            } else {
                return new GenericResponse(false, "Branch id null");
            }
        } catch (Exception e) {
            MastroLogUtils.error(BranchController.class, "Error occured while deleting Branch : {}");
            return new GenericResponse(false, e.getMessage());
        }
    }

}
