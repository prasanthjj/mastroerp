package com.erp.mastro.model.request;

import com.erp.mastro.entities.Modules;
import com.erp.mastro.entities.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class RolesRequestModel {

    private Long id;
    private String roleName;
    private String roleDescription;
    private User user;
    private Set<Modules> modules;

}
