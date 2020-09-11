package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.SalesSlip;
import com.erp.mastro.model.request.DeliveryChellanRequestModel;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.DeliveryChellanService;
import com.erp.mastro.service.interfaces.SalesSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DeliveryChellanController {

    @Autowired
    BranchService branchService;

    @Autowired
    DeliveryChellanService deliveryChellanService;

    @Autowired
    SalesSlipService salesSlipService;

    @RequestMapping("/inventory/getDeliveryChellan")
    public String getDeliveryChellan(Model model) {
        MastroLogUtils.info(DeliveryChellanController.class, "Going to get Delivery Chellan list : {}");
        List<SalesSlip> salesSlipList = salesSlipService.getAllSalesSlips().stream()
                .filter(salesSlip -> (null != salesSlip))
                .filter(salesSlip -> (null != salesSlip.getStatus()))
                .sorted(Comparator.comparing(
                        SalesSlip::getId).reversed())
                .collect(Collectors.toList());

        model.addAttribute("salesSlipForm", new SalesSlipRequestModel());
        model.addAttribute("deliveryChellanForm", new DeliveryChellanRequestModel());
        model.addAttribute("inventoryModule", "inventory");
        model.addAttribute("deliveryChellanTab", "deliveryChellan");
        model.addAttribute("salesSlipList", salesSlipList);
        return "views/dc_purchase_slip";
    }

    @GetMapping("/inventory/getSelectedDeliveryChellan")
    public String getSelectedDeliveryChellan(Model model, HttpServletRequest req) {
        MastroLogUtils.info(DeliveryChellanController.class, "Going to get Selected Delivery Chellan : {}");
        Long branchId = Long.parseLong(req.getParameter("selectedBranchs"));
        Branch branch = branchService.getBranchById(branchId);

        model.addAttribute("deliveryChellanForm", new DeliveryChellanRequestModel());
        model.addAttribute("inventoryModule", "inventory");
        model.addAttribute("deliveryChellanTab", "deliveryChellan");
        model.addAttribute("branchDetails", branch);
            return "views/add_dc";
    }

    @GetMapping("/inventory/addDeliveryChellan")
    public String addDeliveryChellan(Model model) {
        MastroLogUtils.info(DeliveryChellanController.class, "Going to add Delivery Chellan list : {}");
        try {
              List<Branch> branchList = new ArrayList<>();
            for (Branch branch : branchService.getAllBranch()) {
                if (branch.getBranchDeleteStatus() != 1) {
                    branchList.add(branch);
                }
            }

            model.addAttribute("deliveryChellanForm", new DeliveryChellanRequestModel());
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("deliveryChellanTab", "deliveryChellan");
            model.addAttribute("branchList", branchList);

            return "views/add_dc";
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while adding Salry component : {}");
            throw e;
        }

    }

    @PostMapping("/inventory/saveDeliveryChellan")
    public String saveDeliveryChellan(@RequestParam("branch") Set<String> values, @ModelAttribute("deliveryChellanForm") @Valid DeliveryChellanRequestModel deliveryChellanRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(DeliveryChellanController.class, "Going to save Delivery chellan: {}");
        try {
            deliveryChellanService.saveOrUpdateDeliveryChellan(deliveryChellanRequestModel,values);
        } catch (Exception e) {
            MastroLogUtils.error(SalaryComponentController.class, "Error occured while saving Salary Component : {}");
        }
        return "redirect:/inventory/getDeliveryChellan";
    }


}

