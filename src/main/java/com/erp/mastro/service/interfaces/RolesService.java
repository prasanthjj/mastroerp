package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.RolesRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolesService {

    List<Roles> getAllRoles();

    Roles getRolesId(Long id);

    void saveOrUpdateRoles(RolesRequestModel rolesModel);

    void deleteRoles(Long id);

    public void deleteRolesDetails(Long id);
}
