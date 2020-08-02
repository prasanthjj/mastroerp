package com.erp.mastro.model.request;

import com.erp.mastro.entities.Roles;


import java.util.Set;


public class ModuleRequestModel {

    private Long id;
    private String moduleName;
    private Roles roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }


    public static class ModuleModelEdit{
        private String module;
        private Long id;

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

}
