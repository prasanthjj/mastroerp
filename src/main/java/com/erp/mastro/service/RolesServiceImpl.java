package com.erp.mastro.service;

import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.RolesRequestModel;
import com.erp.mastro.repository.RolesRepository;
import com.erp.mastro.service.interfaces.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<Roles> getAllRoles() {
        List<Roles> rolesList = new ArrayList<Roles>();
        rolesRepository.findAll().forEach(roles -> rolesList.add(roles));
        return rolesList;
    }

    @Override
    public Roles getRolesId(Long id) {
        return rolesRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateRoles(RolesRequestModel rolesRequestModel) {

        if (rolesRequestModel.getId() == null) {
            Roles roles = new Roles();
            roles.setRoleName(rolesRequestModel.getRoleName());
            roles.setRoleDescription(rolesRequestModel.getRoleDescription());
            rolesRepository.save(roles);
        } else {
            Roles roles = rolesRepository.findById(rolesRequestModel.getId()).get();
            roles.setRoleName(rolesRequestModel.getRoleName());
            roles.setRoleDescription(rolesRequestModel.getRoleDescription());
            rolesRepository.save(roles);
        }
    }

    @Override
    public void deleteRoles(Long id) {
        rolesRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteRolesDetails(Long id) {
        Roles roles = getRolesId(id);
        roles.setRolesDeleteStatus(1);
        rolesRepository.save(roles);
    }
}
