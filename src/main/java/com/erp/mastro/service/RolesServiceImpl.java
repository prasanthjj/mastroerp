package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.RolesRequestModel;
import com.erp.mastro.repository.RolesRepository;
import com.erp.mastro.service.interfaces.RolesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Role
 */
@Service
public class RolesServiceImpl implements RolesService {

    private Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);

    @Autowired
    private RolesRepository rolesRepository;

    /**
     * method to get all Roles
     *
     * @return roles list
     */
    @Override
    public List<Roles> getAllRoles() {
        List<Roles> rolesList = new ArrayList<Roles>();
        rolesRepository.findAll().forEach(roles -> rolesList.add(roles));
        return rolesList;
    }

    /**
     * method to get role according to id
     *
     * @param id
     * @return role
     */
    @Override
    public Roles getRolesId(Long id) {
        return rolesRepository.findById(id).get();
    }

    /**
     * Save or edit Role
     *
     * @param rolesRequestModel
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateRoles(RolesRequestModel rolesRequestModel) {
        if (rolesRequestModel.getId() == null) {
            MastroLogUtils.info(RolesService.class, "Going to Add Role {}" + rolesRequestModel.toString());
            Roles roles = new Roles();
            roles.setRoleName(rolesRequestModel.getRoleName());
            roles.setRoleDescription(rolesRequestModel.getRoleDescription());
            rolesRepository.save(roles);
            MastroLogUtils.info(RolesService.class, "Added " + roles.getRoleName() + " successfully.");
        } else {
            MastroLogUtils.info(RolesService.class, "Going to edit branch {}" + rolesRequestModel.toString());
            Roles roles = rolesRepository.findById(rolesRequestModel.getId()).get();
            roles.setRoleName(rolesRequestModel.getRoleName());
            roles.setRoleDescription(rolesRequestModel.getRoleDescription());
            rolesRepository.save(roles);
            MastroLogUtils.info(RolesService.class, "Updated " + roles.getRoleName() + " successfully.");
        }
    }

    /**
     * Delete Role
     *
     * @param id
     */
    @Override
    public void deleteRoles(Long id) {
        rolesRepository.deleteById(id);
        MastroLogUtils.info(RolesService.class, "Deleted successfully.");
    }

    /**
     * Delete Role Details
     *
     * @param id
     */
    @Transactional(rollbackOn = {Exception.class})
    public void deleteRolesDetails(Long id) {
        Roles roles = getRolesId(id);
        roles.setRolesDeleteStatus(1);
        rolesRepository.save(roles);
        MastroLogUtils.info(RolesService.class, "Role Details Deleted successfully.");
    }

}
