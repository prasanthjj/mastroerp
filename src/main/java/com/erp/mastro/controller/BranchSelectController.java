package com.erp.mastro.controller;

import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.User;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BranchSelectController {
    @Autowired
    BranchService branchService;
    @Autowired
    UserService userService;

    @Autowired
    UserController userController;
    /**
     * Method to get the selected branch
     *
     * @param branchId
     * @param model
     * @return selected branch name
     */
    @PostMapping("/branch/selectBranchName")
    public String getBranchName(Model model, @Valid Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        String branchName = branch.getBranchName();
        User userDetails = userController.getCurrentUser();
        userService.saveCurrentBranch(branchId,userDetails);
        model.addAttribute("branchName",branchName);
        return "redirect:/home";
    }

}
