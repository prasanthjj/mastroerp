package com.erp.mastro.controller;

import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.ModuleRequestModel;
import com.erp.mastro.model.request.RolesRequestModel;
import com.erp.mastro.service.interfaces.ModuleService;
import com.erp.mastro.service.interfaces.RolesService;
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
public class ModulesController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    RolesService rolesService;

    @RequestMapping("/admin/getRoleAccess")
    public String getRoleAccess(Model model){
        try {
            List<Modules> modulesList = moduleService.getAllModules();
            List<Roles> rolesList = new ArrayList<>();
            for (Roles roles : rolesService.getAllRoles()) {
                if (roles.getRolesDeleteStatus() != 1) {
                    rolesList.add(roles);
                }
            }
            model.addAttribute("moduleForm", new ModuleRequestModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("roleAccessTab", "roleAccess");
            model.addAttribute("modulesList", modulesList);
            model.addAttribute("rolesList", rolesList);
            return "views/role_access_rights";
        }catch (Exception e) {
                throw e;
            }
    }

    @PostMapping("/admin/saveRoleAccess")
    public String saveRoleAccess(@RequestParam("checkboxName") Set<String> values, @ModelAttribute("moduleForm") @Valid ModuleRequestModel moduleRequestModel, HttpServletRequest request, Model model) {
        moduleService.saveOrUpdateModules(moduleRequestModel, values);
        return "redirect:/admin/getRoleAccess";
    }
}
