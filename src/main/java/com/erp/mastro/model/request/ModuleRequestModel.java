package com.erp.mastro.model.request;

import com.erp.mastro.entities.Roles;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class ModuleRequestModel {

    private Long id;
    private String moduleName;
    private Roles roles;

}
