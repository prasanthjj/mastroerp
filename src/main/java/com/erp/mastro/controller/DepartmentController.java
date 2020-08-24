package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Department;
import com.erp.mastro.model.request.DepartmentRequestModel;
import com.erp.mastro.service.interfaces.DepartmentService;
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
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * Method to get Department
     *
     * @param model
     * @return department list
     */

    @RequestMapping("/hr/getDepartment")
    public String getDepartment(Model model) {
        MastroLogUtils.info(DepartmentController.class, "Going to get Department :".toString());
        try {
            List<Department> departmentList = departmentService.getAllDepartment().stream()
                    .filter(departmentData -> (null != departmentData))
                    // .filter(departmentData -> (1 != departmentData.get()))
                    .sorted(Comparator.comparing(
                            Department::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("departmentForm", new DepartmentRequestModel());
            model.addAttribute("hrModule", "hrModule");
            model.addAttribute("departmentTab", "department");
            model.addAttribute("departmentList", departmentList);
            return "views/department_master";
        } catch (Exception e) {
            MastroLogUtils.error(DepartmentController.class, "Error occured while getting department list : {}", e);
            throw e;
        }
    }

    /**
     * Method to save Role
     *
     * @param model
     * @param request
     * @param departmentRequestModel
     * @return saved role details
     */
    @PostMapping("/hr/saveDepartment")
    public String saveDepartment(@ModelAttribute("departmentForm") @Valid DepartmentRequestModel departmentRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(DepartmentController.class, "Going to save Department : {}");
        try {
            departmentService.saveOrUpdateDepartment(departmentRequestModel);
        } catch (Exception e) {
            MastroLogUtils.error(DepartmentController.class, "Error occured while saving Department : {}");
        }
        return "redirect:/hr/getDepartment";
    }

    /**
     * Method to get role for edit
     *
     * @param model
     * @param request
     * @param departmentId
     * @return edited department details
     */
    @GetMapping("/hr/getDepartmentForEdit")
    @ResponseBody
    public GenericResponse getDepartmentForEdit(Model model, HttpServletRequest request, @RequestParam("departmentId") Long departmentId) {
        MastroLogUtils.info(DepartmentController.class, "Going to edit Department : {}" + departmentId);
        Department departDetails = departmentService.getDepartmentById(departmentId);
        return new GenericResponse(true, "get Role details")
                .setProperty("departmentId", departDetails.getId())
                .setProperty("departmentName", departDetails.getDepartmentName())
                .setProperty("departmentHead", departDetails.getDepartmentHead());
    }

    @GetMapping("/hr/activateOrDeactivateDepartment")
    @ResponseBody
    public GenericResponse activateOrDeactivateDepartment(Model model, HttpServletRequest request, @RequestParam("departmentId") Long departmentId) {
        MastroLogUtils.info(DepartmentController.class, "Going to Activate or Deactivate Department :{}" + departmentId);
        Department departDetails = departmentService.getDepartmentById(departmentId);
        departmentService.activateOrDeactivateDepartment(departmentId);
        return new GenericResponse(true, "get User details")
                .setProperty("departmentId", departDetails.getId());
    }

}
