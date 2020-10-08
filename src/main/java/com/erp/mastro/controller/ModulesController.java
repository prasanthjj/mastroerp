package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.ModuleRequestModel;
import com.erp.mastro.repository.ModuleRepository;
import com.erp.mastro.service.interfaces.ModuleService;
import com.erp.mastro.service.interfaces.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ModulesController {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleService moduleService;

    @Autowired
    RolesService rolesService;

    /**
     * Method to get role access
     *
     * @param model
     * @return module list page
     */
    @RequestMapping("/admin/getRoleAccess")
    public String getRoleAccess(Model model) {
        MastroLogUtils.info(ModulesController.class, "Going to get role access : {}");
        try {
            Iterable<Modules> modulesList = moduleRepository.findAll();
            Set<Modules> modulesSet = new HashSet<>();
            for (Modules modules : modulesList) {
                modulesSet.add(modules);
            }
            List<Roles> rolesList = new ArrayList<>();
            for (Roles roles : rolesService.getAllRoles()) {
                if (roles.getRolesDeleteStatus() != 1) {
                    rolesList.add(roles);
                }
            }
            model.addAttribute("moduleForm", new ModuleRequestModel());
            model.addAttribute(Constants.ADMIN_MODULE, Constants.ADMIN_MODULE);
            model.addAttribute("roleAccessTab", "roleAccess");
            model.addAttribute("modulesList", modulesSet);
            model.addAttribute("rolesList", rolesList);
            return "views/role_access_rights";
        } catch (Exception e) {
            MastroLogUtils.error(ModulesController.class, "Error occured while getting role access rights : {}");
            throw e;
        }
    }

    /**
     * Method to edit role access
     *
     * @param model
     * @param request
     * @param roleId
     * @return edited role access details
     */
    @GetMapping("/admin/getRoleAccessEdit")
    @ResponseBody
    public GenericResponse getRoleAccessForEdit(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        MastroLogUtils.info(ModulesController.class, "Going to edit role access rights : {}" + roleId);
        Set<Modules> modulesList = new HashSet<>();
        for (Modules modules : moduleService.getAllModules()) {
            modulesList.add(modules);
        }
        Roles roles = rolesService.getRolesId(roleId);
        Set<Modules> roleModules = roles.getModules();
        Set<ModuleRequestModel.ModuleModelEdit> moduleModelEdits = new HashSet<>();
        for (Modules modules : roleModules) {
            ModuleRequestModel.ModuleModelEdit moduleModelEdit = new ModuleRequestModel.ModuleModelEdit();
            moduleModelEdit.setModule(modules.getModuleName());
            moduleModelEdit.setId(modules.getId());
            moduleModelEdits.add(moduleModelEdit);
        }
        Set<ModuleRequestModel.ModuleModelEdit> fullModuleModelEdits = new HashSet<>();
        for (Modules modules : modulesList) {
            ModuleRequestModel.ModuleModelEdit editModuleModel = new ModuleRequestModel.ModuleModelEdit();
            editModuleModel.setModule(modules.getModuleName());
            editModuleModel.setId(modules.getId());
            fullModuleModelEdits.add(editModuleModel);
        }
        Iterator<ModuleRequestModel.ModuleModelEdit> finalSet = fullModuleModelEdits.iterator();
        for (Iterator<ModuleRequestModel.ModuleModelEdit> it = finalSet; it.hasNext(); ) {
            ModuleRequestModel.ModuleModelEdit fullModel = it.next();
            if (fullModel != null) {
                for (ModuleRequestModel.ModuleModelEdit roleModel : moduleModelEdits) {
                    if (fullModel.getId() == roleModel.getId()) {
                        finalSet.remove();
                    }
                }
            }
        }
        return new GenericResponse(true, "get RoleAccess details")
                .setProperty("rolemodules", moduleModelEdits)
                .setProperty("remainingModules", fullModuleModelEdits);
    }

    /**
     * Method to save role access rights
     *
     * @param moduleRequestModel
     * @param request
     * @param model
     * @param values
     * @return saved access details in role access list
     */
    @PostMapping("/admin/saveRoleAccess")
    public String saveRoleAccess(@RequestParam("checkboxName") Set<String> values, @ModelAttribute("moduleForm") @Valid ModuleRequestModel moduleRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(ModulesController.class, "Going to save role access rights : {}");
        try {
            moduleService.saveOrUpdateModules(moduleRequestModel, values);
            return "redirect:/admin/getRoleAccess";
        } catch (Exception e) {
            MastroLogUtils.error(ModulesController.class, "Error occured while saving role access rights : {}", e);
            throw e;
        }
    }

}
