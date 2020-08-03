package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.RolesRequestModel;
import com.erp.mastro.service.interfaces.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RolesController {

    @Autowired
    RolesService rolesService;

    @RequestMapping("/admin/getRole")
    public String getRole(Model model) {
        try {
            List<Roles> rolesList = new ArrayList<>();
            for (Roles roles : rolesService.getAllRoles()) {
                if (roles.getRolesDeleteStatus() != 1) {
                    rolesList.add(roles);
                }
            }
            model.addAttribute("roleForm", new RolesRequestModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("rolesTab", "roles");
            model.addAttribute("rolesList", rolesList);
            return "views/role_master";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/admin/saveRole")
    public String saveRole(@ModelAttribute("roleForm") @Valid RolesRequestModel rolesRequestModel, HttpServletRequest request, Model model) {
        rolesService.saveOrUpdateRoles(rolesRequestModel);
        return "redirect:/admin/getRole";
    }

    @GetMapping("/admin/getRoleForEdit")
    @ResponseBody
    public GenericResponse getRoleForEdit(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {

        Roles roleDetails = rolesService.getRolesId(roleId);
        return new GenericResponse(true, "get Role details")
                .setProperty("roleId", roleDetails.getId())
                .setProperty("roleName", roleDetails.getRoleName())
                .setProperty("roleDescription", roleDetails.getRoleDescription());

    }

    @PostMapping("/admin/deleteRolesDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        try {
            rolesService.deleteRolesDetails(roleId);
            return new GenericResponse(true, "delete Role Details");
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

}
