package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Department;
import com.erp.mastro.model.request.DepartmentRequestModel;
import com.erp.mastro.repository.DepartmentRepository;
import com.erp.mastro.service.interfaces.DepartmentService;
import com.erp.mastro.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Department
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * method to get all Departments
     *
     * @return Department list
     */
    @Override
    public List<Department> getAllDepartment() {
        List<Department> departmentList = new ArrayList<Department>();
        departmentRepository.findAll().forEach(departments -> departmentList.add(departments));
        return departmentList;
    }

    /**
     * method to get Department according to id
     *
     * @param id
     * @return department
     */
    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).get();
    }

    /**
     * Save or edit Department
     *
     * @param departmentRequestModel
     */

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateDepartment(DepartmentRequestModel departmentRequestModel) {
        if (departmentRequestModel.getId() == null) {
            MastroLogUtils.info(DepartmentService.class, "Going to Add Department {}" + departmentRequestModel.toString());
            Department department = new Department();
            department.setDepartmentName(departmentRequestModel.getDepartmentName());
            department.setDepartmentHead(departmentRequestModel.getDepartmentHead());
            department.setStatus(true);
            departmentRepository.save(department);
            MastroLogUtils.info(DepartmentService.class, "Added " + department.getDepartmentName() + " successfully.");

        }
      /*  else if (!departmentRequestModel.isStatus()) {
            Department department = departmentRepository.findById(departmentRequestModel.getId()).get();
            MastroLogUtils.info(DepartmentService.class, "Going to Enable Department {}" + departmentRequestModel.toString());
            Department.setStatus(DepartmentRequestModel.isStatus());
        }*/
        else {
            MastroLogUtils.info(DepartmentService.class, "Going to edit department {}" + departmentRequestModel.toString());
            Department department = departmentRepository.findById(departmentRequestModel.getId()).get();
            department.setDepartmentName(departmentRequestModel.getDepartmentName());
            department.setDepartmentHead(departmentRequestModel.getDepartmentHead());
            departmentRepository.save(department);
            MastroLogUtils.info(DepartmentService.class, "Updated " + department.getDepartmentName() + " successfully.");

        }

    }

    /**
     * Activate or Deactivate Department
     *
     * @param id
     */
    @Override
    public void activateOrDeactivateDepartment(Long id) {
        if (id != null) {
            Department department = getDepartmentById(id);
            if (department.isStatus()) {
                department.setStatus(false);
            } else {
                department.setStatus(true);
            }
            departmentRepository.save(department);
            MastroLogUtils.info(UserService.class, "Activate or Deactivate " + department.getDepartmentName() + " successfully.");
        } else {
            MastroLogUtils.info(UserService.class, "Department Id null");
        }
    }

}
