package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.ModuleRequestModel;
import com.erp.mastro.model.request.RolesRequestModel;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.repository.ModuleRepository;
import com.erp.mastro.service.interfaces.ModuleService;
import com.erp.mastro.service.interfaces.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ModulesController {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    ModuleService moduleService;

    @Autowired
    RolesService rolesService;

    @RequestMapping("/admin/getRoleAccess")
    public String getRoleAccess(Model model){
        try {
           /* List<Modules> modulesList = moduleService.getAllModules();*/
            Iterable<Modules> modulesList = moduleRepository.findAll();
            Set<Modules> modulesSet  = new HashSet<>();
            for(Modules modules :modulesList) {
                modulesSet.add(modules);
            }
            List<Roles> rolesList = new ArrayList<>();
            for (Roles roles : rolesService.getAllRoles()) {
                if (roles.getRolesDeleteStatus() != 1) {
                    rolesList.add(roles);
                }
            }

            model.addAttribute("moduleForm", new ModuleRequestModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("roleAccessTab", "roleAccess");
            model.addAttribute("modulesList", modulesSet);
            model.addAttribute("rolesList", rolesList);
            return "views/role_access_rights";
        }catch (Exception e) {
                throw e;
            }
    }

    @GetMapping("/admin/getRoleAccessEdit")
    @ResponseBody
    public GenericResponse getRoleAccessForEdit(Model model, HttpServletRequest request, @RequestParam("roleId") Long roleId) {
        System.out.println("test ajax");
        System.out.println("inside the getRoleAccessForEdit");
        //for all module
       Set<Modules> modulesList = new HashSet<>();
        for (Modules modules : moduleService.getAllModules()) {
            System.out.println("modules id :::::: " + modules.getId());
            modulesList.add(modules);
        }
        System.out.println("modulesList Size :" + modulesList.size());

        Roles roles = rolesService.getRolesId(roleId);
        Set<Modules> roleModules = roles.getModules();

         Set<ModuleRequestModel.ModuleModelEdit> moduleModelEdits = new HashSet<>();
            for (Modules modules : roleModules){
                ModuleRequestModel.ModuleModelEdit moduleModelEdit = new  ModuleRequestModel.ModuleModelEdit();
                moduleModelEdit.setModule(modules.getModuleName());
                moduleModelEdit.setId(modules.getId());
                System.out.println("moduleModelEdit id :::::: " + moduleModelEdit.getId());
                moduleModelEdits.add(moduleModelEdit);
            }

            System.out.println("moduleModelEdits Size : " + moduleModelEdits.size());

            Set<ModuleRequestModel.ModuleModelEdit> fullModuleModelEdits = new HashSet<>();
            for (Modules modules : modulesList){
                ModuleRequestModel.ModuleModelEdit moduleModelEdit = new  ModuleRequestModel.ModuleModelEdit();
                moduleModelEdit.setModule(modules.getModuleName());
                moduleModelEdit.setId(modules.getId());
                System.out.println("moduleModelEdit id :::::: " + moduleModelEdit.getId());
                fullModuleModelEdits.add(moduleModelEdit);
            }
            System.out.println("fullModuleModelEdits size before Removing duplicate :::: " + fullModuleModelEdits.size());
            for(ModuleRequestModel.ModuleModelEdit fullModuleModel : fullModuleModelEdits) {
                for (ModuleRequestModel.ModuleModelEdit moduleModel : moduleModelEdits) {
                    if (fullModuleModel.getId().equals(moduleModel.getId())) {
                        System.out.println("In if ");
                        fullModuleModelEdits.remove(moduleModel);
                    } else {
                        System.out.println("In Else ");
                    }
                }
            }


           /* boolean isNotEqual = false;
            for(Modules modules : modulesList ){
                ModuleRequestModel.ModuleModelEdit editModuleModel = new  ModuleRequestModel.ModuleModelEdit();
                for(ModuleRequestModel.ModuleModelEdit moduleModelEdit : moduleModelEdits) {
                    *//*ModuleRequestModel.ModuleModelEdit editModuleModel = new  ModuleRequestModel.ModuleModelEdit();*//*
                    if(moduleModelEdit.getId().equals(modules.getId())) {
                        isNotEqual = false;
                        System.out.println("modules id ***** " + modules.getId());
                        System.out.println("isNotEqual in if ***** " + isNotEqual);
                    } else {
                        isNotEqual = true;
                        System.out.println("isNotEqual In else ***** " + isNotEqual);
                    }
                }
                if(isNotEqual == false) {
                    editModuleModel.setModule(modules.getModuleName());
                    editModuleModel.setId(modules.getId());
                    System.out.println("editModuleModel id %%%%%%%%%% " + editModuleModel.getId());
                    fullModuleModelEdits.add(editModuleModel);
                }
            }*/



       /* System.out.println("fullModuleModelEdits Size : " + fullModuleModelEdits.size());
        List<ModuleRequestModel.ModuleModelEdit> union = new ArrayList<>(moduleModelEdits);
        System.out.println("union :::::: "+ union.size());
        union.addAll(fullModuleModelEdits);
        System.out.println("Union after adding fullModuleModelEdits ::::: "+ union.size());

        List<ModuleRequestModel.ModuleModelEdit> intersection = new ArrayList<>(moduleModelEdits);
        System.out.println("intersection ::::: "+ intersection.size());
        union.removeAll(intersection);
        System.out.println("Union after removing intersection ::::: "+ union.size());

        Set<ModuleRequestModel.ModuleModelEdit> remainingModules = new HashSet<>();
        for (ModuleRequestModel.ModuleModelEdit n : union) {
            remainingModules.add(n);
        }*/
        /*for(ModuleRequestModel.ModuleModelEdit modules : fullModuleModelEdits){

        }*/
       /* remainingModules.removeAll(intersection);
        System.out.println("moduleModelEdits :::: " + moduleModelEdits.size());
        System.out.println("remainingModules :::: " + remainingModules.size());*/

        System.out.println("moduleModelEdits :::: " + moduleModelEdits.size());
        System.out.println("fullModuleModelEdits :::: " + fullModuleModelEdits.size());
        return new GenericResponse(true,"get RoleAccess details")
                .setProperty("roleodules",moduleModelEdits)
                .setProperty("remainingModules",fullModuleModelEdits);
    }

    @PostMapping("/admin/saveRoleAccess")
    public String saveRoleAccess(@RequestParam("checkboxName") Set<String> values, @ModelAttribute("moduleForm") @Valid ModuleRequestModel moduleRequestModel, HttpServletRequest request, Model model) {
        moduleService.saveOrUpdateModules(moduleRequestModel, values);
        return "redirect:/admin/getRoleAccess";
    }
}
