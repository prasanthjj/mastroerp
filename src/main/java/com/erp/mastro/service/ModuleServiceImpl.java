package com.erp.mastro.service;

import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.ModuleRequestModel;
import com.erp.mastro.repository.ModuleRepository;
import com.erp.mastro.repository.RolesRepository;
import com.erp.mastro.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public List<Modules> getAllModules() {
        List<Modules> modulesList = new ArrayList<Modules>();
        moduleRepository.findAll().forEach(modules ->modulesList.add(modules) );
        return modulesList;
    }

    @Override
    public Modules getModuleId(Long id) {
        return moduleRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateModules(ModuleRequestModel moduleRequestModel, Set<String> values) {

        Iterable<Modules> modules= moduleRepository.findAll();
             Roles role = moduleRequestModel.getRoles();
            Set<Modules> moduleSet = role.getModules();
            for (String id : values) {
                Modules module = moduleRepository.findById(Long.valueOf(id)).get();
                moduleSet.add(module);
            }

            role.setModules(moduleSet);
            rolesRepository.save(role);

    }



    @Override
    public void deleteModules(Long id) { moduleRepository.deleteById(id); }

}
