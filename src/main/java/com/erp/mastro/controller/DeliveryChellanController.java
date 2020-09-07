package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.model.request.DeliveryChellanRequestModel;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.DeliveryChellanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class DeliveryChellanController {

    @Autowired
    BranchService branchService;

    @Autowired
    DeliveryChellanService deliveryChellanService;

    @RequestMapping("/inventory/getDeliveryChellan")
    public String getDeliveryChellan(Model model) {
        /*Branch branch = branchService.getBranchById(branchId);
        List<Branch> branchList = new ArrayList<>();

        System.out.println("branch name:"+branch.getBranchName());
        System.out.println("id"+branchId);
        */


        model.addAttribute("deliveryChellanForm", new DeliveryChellanRequestModel());
        model.addAttribute("inventoryModule", "inventory");
        model.addAttribute("deliveryChellanTab", "deliveryChellan");
        //  model.addAttribute("deliveryChellanList", deliveryChellanList);
/*
        model.addAttribute("branchList", branchList);
*/

        return "views/dc_purchase_slip";
    }

    @GetMapping("/inventory/addDeliveryChellan")
    public String addDeliveryChellan(Model model, @Valid Long branchId) {
        MastroLogUtils.info(DeliveryChellanController.class, "Going to add Delivery Chellan list : {}");
        try {
            /*List<SalaryComponent> salaryComponentList = salaryComponentService.getAllSalaryComonents().stream()
                    .filter(salarycomponentData -> (null != salarycomponentData))

                    .sorted(Comparator.comparing(
                            SalaryComponent::getId).reversed())
                    .collect(Collectors.toList());*/
            System.out.println("id:"+branchId);
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

