package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RolesController {

    @Autowired
    RolesService rolesService;

    /**
     * Method to get Role
     *
     * @param model
     * @return roles list
     */
    @RequestMapping("/admin/getRole")
    public String getRole(Model model) {
        MastroLogUtils.info(RolesController.class, "Going to get Roles : {}");
        try {
            List<Roles> rolesList = rolesService.getAllRoles().stream()
                    .filter(roleData -> (null != roleData))
                    .filter(roleData -> (1 != roleData.getRolesDeleteStatus()))
                    .sorted(Comparator.comparing(
                            Roles::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("roleForm", new RolesRequestModel());
            model.addAttribute(Constants.ADMIN_MODULE, Constants.ADMIN_MODULE);
            model.addAttribute("rolesTab", "roles");
            model.addAttribute("rolesList", rolesList);
            return "views/role_master";
        } catch (Exception e) {
            MastroLogUtils.error(RolesController.class, "Error occured while getting roles list : {}", e);
            throw e;
        }
    }

    /**
     * Method to save Role
     *
     * @param model
     * @param request
     * @param rolesRequestModel
     * @return saved role details
     */
    @PostMapping("/admin/saveRole")
    public String saveRole(@ModelAttribute("roleForm") @Valid RolesRequestModel rolesRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(RolesController.class, "Going to save Role : {}");
        try {
            rolesService.saveOrUpdateRoles(rolesRequestModel);
        } catch (Exception e) {
            MastroLogUtils.error(RolesController.class, "Error occured while saving Branch : {}");
        }
        return "redirect:/admin/getRole";
    }

    /**
     * Method to get role for edit
     *
     * @param model
     * @param request
     * @param roleId
     * @return edited role details
     */
    @GetMapping("/admin/getRoleForEdit")
    @ResponseBody
    public GenericResponse getRoleForEdit(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        MastroLogUtils.info(RolesController.class, "Going to edit Role : {}" + roleId);
        Roles roleDetails = rolesService.getRolesId(roleId);
        return new GenericResponse(true, "get Role details")
                .setProperty("roleId", roleDetails.getId())
                .setProperty("roleName", roleDetails.getRoleName())
                .setProperty("roleDescription", roleDetails.getRoleDescription());
    }

    /**
     * method to delete the role details
     *
     * @param request
     * @param roleId
     * @param model
     * @return deleted roles list
     */
    @PostMapping("/admin/deleteRolesDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        MastroLogUtils.info(RolesController.class, "Going to delete Role : {}" + roleId);
        try {
            if (roleId != null) {
                rolesService.deleteRolesDetails(roleId);
                return new GenericResponse(true, "delete Role Details");
            } else {
                return new GenericResponse(false, "Role id null");
            }
        } catch (Exception e) {
            MastroLogUtils.error(RolesController.class, "Error occurred while deleting Roles : {}");
            return new GenericResponse(false, e.getMessage());
        }
    }

}
