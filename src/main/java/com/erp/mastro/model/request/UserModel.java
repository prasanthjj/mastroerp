package com.erp.mastro.model.request;

import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.Employee;
import com.erp.mastro.entities.Roles;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class UserModel {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private boolean enabled;
    private Set<Roles> roles;
    private Set<Branch> branch;
    private Employee employee;
    private String currentBranch;

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class UserModelEdit {

        private String role;
        private Long id;
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class RolemodelEdit {

        private String role;
        private Long roleid;
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class UserModelBranchEdit {

        private String branchname;
        private Long id;
    }

}
