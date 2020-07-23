package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Modules;
import com.erp.mastro.model.request.ModuleRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ModuleService {

    List<Modules> getAllModules();

    Modules getModuleId(Long id);

    void saveOrUpdateModules(ModuleRequestModel moduleRequestModel, Set<String> values);

    void deleteModules(Long id );

}
