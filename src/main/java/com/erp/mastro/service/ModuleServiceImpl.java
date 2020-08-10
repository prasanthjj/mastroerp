package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.model.request.ModuleRequestModel;
import com.erp.mastro.repository.ModuleRepository;
import com.erp.mastro.repository.RolesRepository;
import com.erp.mastro.service.interfaces.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for Modules
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    private Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    RolesRepository rolesRepository;

    /**
     * method to get all modules
     *
     * @return modules list
     */
    @Override
    public List<Modules> getAllModules() {
        List<Modules> modulesList = new ArrayList<Modules>();
        moduleRepository.findAll().forEach(modules -> modulesList.add(modules));
        return modulesList;
    }

    /**
     * method to get module according to id
     *
     * @param id
     * @return module
     */
    @Override
    public Modules getModuleId(Long id) {
        return moduleRepository.findById(id).get();
    }

    /**
     * Save or edit access rights
     *
     * @param moduleRequestModel
     * @param values
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateModules(ModuleRequestModel moduleRequestModel, Set<String> values) {
        MastroLogUtils.info(ModuleService.class, "Going to Add access rights {}");
        Iterable<Modules> modules = moduleRepository.findAll();
        Roles role = moduleRequestModel.getRoles();
        Set<Modules> moduleSet = new HashSet<>();
        role.setModules(moduleSet);
        for (String id : values) {
            Modules module = moduleRepository.findById(Long.valueOf(id)).get();
            moduleSet.add(module);
        }
        role.setModules(moduleSet);
        rolesRepository.save(role);
        MastroLogUtils.info(ModuleService.class, "Added successfully.");
    }

    /**
     * Delete Module
     *
     * @param id
     */
    @Override
    public void deleteModules(Long id) {
        if (id != null) {
            moduleRepository.deleteById(id);
            MastroLogUtils.info(ModuleService.class, "Deleted successfully.");
        }
    }

}
