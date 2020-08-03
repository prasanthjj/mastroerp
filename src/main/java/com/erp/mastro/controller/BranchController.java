package com.erp.mastro.controller;

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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @RequestMapping("/addBranch")
    public String addBranch(Model model) {
        model.addAttribute("branchForm", new BranchRequestModel());
        model.addAttribute("adminModule", "adminModule");
        model.addAttribute("branchTab", "branch");
        return "views/create_branch";
    }

    @PostMapping("/saveBranch")
    public String saveBranch(@ModelAttribute("branchForm") @Valid BranchRequestModel branchRequestModel, HttpServletRequest request, Model model) {
        try {
            branchService.saveOrUpdateBranch(branchRequestModel);
            return "redirect:/admin/getBranch";
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping("/getBranch")
    public String getBranch(Model model) {
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
            throw e;
        }
    }

    @RequestMapping("/editBranch")
    public String editBranch(HttpServletRequest request, @RequestParam("branchId") Long branchId, Model model) {
        try {
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("branchTab", "branch");
            model.addAttribute("branchForm", new BranchRequestModel(branchService.getBranchById(branchId)));
            return "views/edit_branch";
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/viewBranch")
    public String getViewBranch(Model model, @RequestParam("branchId") Long branchId, HttpServletRequest req) {
        try {
            model.addAttribute("branchForm", branchService.getBranchById(branchId));
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("branchTab", "branch");
            return "views/view_branch";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/deleteBranchDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("branchId") Long branchId) {
        try {
            branchService.deleteBranchDetails(branchId);
            return new GenericResponse(true, "delete Branch Details");
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

}
