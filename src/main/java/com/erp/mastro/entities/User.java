package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "user")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "user_name")
    protected String userName;

    @Column(name = "created_by")
    protected String createdBy;

    @Column(name = "created_date")
    protected Date createdDate;

    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    @Column(name = "last_modified_date")
    protected Date lastModifiedDate;

    @Column(name = "password")
    protected String password;

    @Column(name = "email")
    protected String email;

    @Column(name = "enabled", nullable = false)
    protected boolean enabled;

    @Column(name = "current_login")
    protected Date currentLogin;

    @Column(name = "last_login")
    protected Date lastLogin;

    @Column(name = "logged_in", nullable = false)
    protected boolean loggedIn;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    Set<Roles> roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_branch", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "branch_id", referencedColumnName = "id")})
    private Set<Branch> branch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_selected_branch_id")
    private UserSelectedBranch userSelectedBranch;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
